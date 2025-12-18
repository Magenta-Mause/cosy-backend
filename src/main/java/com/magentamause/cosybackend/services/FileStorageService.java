package com.magentamause.cosybackend.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service
public class FileStorageService {

    /**
     * Immutable description of a single file system entry under storage.
     *
     * @param name the simple name of the entry (file or directory), without any parent path
     * @param isDirectory {@code true} if this entry is a directory, {@code false} if it is a regular file
     * @param size the size of the entry in bytes. For files, this is the file size as reported by
     *             {@link java.nio.file.Files#size(Path)}. For directories, this is the value returned
     *             by {@link java.nio.file.Files#size(Path)}, which is implementation-specific and
     *             does not represent the recursive size of all contents.
     */
    public record FileInfo(String name, boolean isDirectory, long size) {}

    public List<FileInfo> listFiles(Path rootPath, String relativePath) throws IOException {
        Path path = resolveAndValidatePath(rootPath, relativePath);
        try (Stream<Path> stream = Files.list(path)) {
            return stream.map(
                            p -> {
                                try {
                                    return new FileInfo(
                                            p.getFileName().toString(),
                                            Files.isDirectory(p),
                                            Files.size(p));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            })
                    .toList();
        }
    }

    public byte[] readFile(Path rootPath, String relativePath) throws IOException {
        Path path = resolveAndValidatePath(rootPath, relativePath);
        return Files.readAllBytes(path);
    }

    public void writeFile(Path rootPath, String relativePath, byte[] content) throws IOException {
        Path path = resolveAndValidatePath(rootPath, relativePath);
        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }
        Files.write(path, content);
    }

    public void createDirectory(Path rootPath, String relativePath) throws IOException {
        Path path = resolveAndValidatePath(rootPath, relativePath);
        Files.createDirectories(path);
    }

    public void delete(Path rootPath, String relativePath) throws IOException {
        Path path = resolveAndValidatePath(rootPath, relativePath);
        if (Files.isDirectory(path)) {
            try (Stream<Path> walk = Files.walk(path)) {
                List<Path> pathsToDelete = walk.sorted(Comparator.reverseOrder()).toList();
                for (Path p : pathsToDelete) {
                    Files.deleteIfExists(p);
                }
            }
        } else {
            Files.deleteIfExists(path);
        }
    }

    Path resolveAndValidatePath(Path rootPath, String relativePath) {
        if (relativePath == null) {
            relativePath = "";
        }
        // Remove leading slash to ensure it's treated as relative
        if (relativePath.startsWith("/")) {
            relativePath = relativePath.substring(1);
        }

        Path resolved = rootPath.resolve(relativePath).normalize();
        if (!resolved.startsWith(rootPath.normalize())) {
            throw new SecurityException("Access denied: Path is outside the allowed scope.");
        }
        return resolved;
    }
}
