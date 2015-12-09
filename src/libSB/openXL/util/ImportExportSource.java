/* 
 * The MIT License
 *
 * Copyright 2015 Simon Berndt.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package libSB.openXL.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author Simon Berndt
 */
public final class ImportExportSource {

    private static final Logger LOG = Logger.getLogger(ImportExportSource.class.getName());

    private ImportExportSource() {
    }

    public static void exportSource(Path destination, String shaderSource) {
        Objects.requireNonNull(destination);
        Objects.requireNonNull(shaderSource);
        try {
            if (destination.getParent() != null) {
                Files.createDirectories(destination.getParent());
            }
            try (BufferedWriter writer = Files.newBufferedWriter(destination, StandardCharsets.UTF_8, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE)) {
                writer.append(shaderSource);
                writer.flush();
            }
        } catch (IOException e) {
            LOG.log(Level.WARNING, null, e);
        }
    }

    public static Optional<String> importSource(Path source) {
        Objects.requireNonNull(source);

        StringBuilder sourceCode = new StringBuilder();
        try (Stream<String> lines = Files.lines(source, StandardCharsets.UTF_8)) {
            lines.forEach((String line) -> sourceCode.append(line).append(System.lineSeparator()));
            return Optional.ofNullable(sourceCode.toString());
        } catch (IOException e) {
            return Optional.empty();
        }
    }
    
    public static Optional<String> importSource(InputStream source) {
        Objects.requireNonNull(source);

        StringBuilder sourceCode = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(source, StandardCharsets.UTF_8))) {
            reader.lines().forEach((String line) -> sourceCode.append(line).append(System.lineSeparator()));
            return Optional.ofNullable(sourceCode.toString());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
