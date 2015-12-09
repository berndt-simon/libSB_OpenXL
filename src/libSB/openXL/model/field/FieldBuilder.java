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

import java.util.logging.Logger;
import libSB.openXL.model.TypeDefQualifier;

/**
 *
 * @author Simon Berndt
 */
public class FieldBuilder {

    private static final Logger LOG = Logger.getLogger(FieldBuilder.class.getName());

    protected final TypeDefQualifier dataType;
    protected final String identifier;
    protected int pointer;
    protected int array;

    protected FieldBuilder(TypeDefQualifier typeDefQualifier, String identifier) {
        this.dataType = typeDefQualifier;
        this.identifier = identifier;
        this.pointer = 0;
        this.array = 0;
    }
    
    /**
     * shortcut for pointer(1);
     */    
    public FieldBuilder pointer() {
        return pointer(1);
    }

    public FieldBuilder pointer(int pointerLevel) {
        if (pointerLevel < 0) {
            throw new IllegalArgumentException("Pointer-Level can't be negative");
        }
        this.pointer = pointerLevel;
        return this;
    }

    public FieldBuilder array(int arraySize) {
        if (arraySize < 1) {
            throw new IllegalArgumentException("Array-Size can't be negative, or null");
        }
        if (arraySize == 1) {
            throw new IllegalArgumentException("Array-Size of one does not make sense");
        }
        this.array = arraySize;
        return this;
    }

    public Field build() {
        return new Field(dataType, pointer, identifier, array);
    }

}
