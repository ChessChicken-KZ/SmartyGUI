/**
 * Copyright 2022 ChessChicken-KZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kz.chesschicken.smartygui.commonloader.guiframework.widgets;

import kz.chesschicken.smartygui.commonloader.BinaryIntFunction;
import kz.chesschicken.smartygui.commonloader.guiframework.ValueXY;
import kz.chesschicken.smartygui.commonloader.guiframework.api.IRunQ1W9M;

public class WidgetTextFieldNum extends WidgetTextField {

    protected final IRunQ1W9M runQ1W9M;

    public WidgetTextFieldNum(int w, int h, BinaryIntFunction<ValueXY> f, IRunQ1W9M q) {
        super(w, h, f);
        this.runQ1W9M = q;
    }

    public WidgetTextFieldNum(String t, int w, int h, BinaryIntFunction<ValueXY> f, IRunQ1W9M q) {
        super(t, w, h, f);
        this.runQ1W9M = q;
    }

    boolean __isNum(char a) {
        return a == '0' || a == '1' || a == '2' || a == '3' || a == '4' || a == '5' || a == '6' || a == '7' || a == '8' || a == '9';
    }

    @Override
    public void typeKey(char c, int i) {
        if (this.isEnabled && this.isFocused) {
            if(c == '\b' && this.text.length() > 0) {
                this.text = this.text.substring(0, this.text.length() - 1);
                return;
            }
            if(__isNum(c)) {
                super.typeKey(c, i);
                if(this.text.length() > 0 && runQ1W9M != null)
                    runQ1W9M.runQ1W9M();
            }
        }
    }
}
