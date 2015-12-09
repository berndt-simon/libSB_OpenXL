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

import java.util.Arrays;

/**
 *
 * @author Simon Berndt
 */
public class AbstractCodeBuilder {

    protected static final String NL = System.lineSeparator();
    protected static final String TAB = "\t";

    protected final StringBuilder code;

    public AbstractCodeBuilder(StringBuilder code) {
        this.code = code;
    }
    
    public void newLine() {
        code.append(NL);
    }

    public void comment(String comment) {
        code.append("//").append(comment);
    }

    public void blockComment(String comment, boolean indent) {
        code.append("/*").append(NL);
        if (indent) {
            Arrays.stream(comment.split(NL)).forEach((String line) -> {
                code.append(TAB).append(line).append(NL);
            });
        } else {
            code.append(comment).append(NL);
        }
        code.append("*/").append(NL);
    }
    
    public void raw(String rawCode) {
        code.append(rawCode);
    }

}
