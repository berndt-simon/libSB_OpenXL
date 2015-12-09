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
package libSB.openXL.model.field;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.logging.Level;
import java.util.logging.Logger;
import libSB.openXL.model.TypeDefQualifier;

/**
 *
 * @author Simon Berndt
 */
public class Field {

    private static final Logger LOG = Logger.getLogger(Field.class.getName());

    private final TypeDefQualifier type;
    private final String identifier;
    private final int pointer;
    private final int array;

    protected Field(TypeDefQualifier type, int pointerLevel, String identifier, int arraySize) {
        this.identifier = identifier;
        this.type = type;
        this.pointer = pointerLevel;
        this.array = arraySize;
    }

    public TypeDefQualifier getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public OptionalInt getPointerLevel() {
        if (isPointer()) {
            return OptionalInt.of(pointer);
        }
        return OptionalInt.empty();
    }

    public boolean isPointer() {
        return pointer > 0;
    }

    public boolean isArray() {
        return array > 1;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.identifier);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Field other = (Field) obj;
        if (!Objects.equals(this.identifier, other.identifier)) {
            return false;
        }
        return true;
    }

    public OptionalInt getArraySize() {
        if (isArray()) {
            return OptionalInt.of(array);
        }
        return OptionalInt.empty();
    }

    /**
     * @return size of this field as bytes
     */
    public int getSizeOf() {
        if (isPointer()) {
            throw new UnsupportedOperationException("Size for Pointer-Fields is currently unsupported");
        }
        int bytes = type.getSizeOf();
        OptionalInt arraySize = getArraySize();
        if (arraySize.isPresent()) {
            bytes *= arraySize.getAsInt();
        }
        return bytes;
    }

    public static FieldBuilder builder(TypeDefQualifier typeDefQualifier, String identifier) {
        Objects.requireNonNull(identifier);
        Objects.requireNonNull(typeDefQualifier);
        return new FieldBuilder(typeDefQualifier, identifier);
    }

    public static TypeDefQualifier customTypeDef(String customDataType, int sizeOf) {
        Objects.requireNonNull(customDataType);
        LOG.log(Level.WARNING, "Unchecked Datatype \'{0}\' with {1} Bytes - use with caution", new Object[]{customDataType, sizeOf});
        return new CustomDataType(customDataType, sizeOf);
    }

}
