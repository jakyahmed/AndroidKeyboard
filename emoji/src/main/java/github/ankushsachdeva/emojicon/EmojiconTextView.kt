/*
 * Copyright 2014 Ankush Sachdeva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package github.ankushsachdeva.emojicon

import android.content.Context
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * @author Hieu Rocker (rockerhieu@gmail.com).
 */
class EmojiconTextView : AppCompatTextView {
    private var mEmojiconSize = 0
    private var mTextStart = 0
    private var mTextLength = -1

    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs == null) {
            mEmojiconSize = textSize.toInt()
        } else {
            val a = context.obtainStyledAttributes(attrs, R.styleable.Emojicon)
            mEmojiconSize = a.getDimension(R.styleable.Emojicon_emojiconSize, textSize).toInt()
            mTextStart = a.getInteger(R.styleable.Emojicon_emojiconTextStart, 0)
            mTextLength = a.getInteger(R.styleable.Emojicon_emojiconTextLength, -1)
            a.recycle()
        }
        text = text
    }

    override fun setText(text: CharSequence, type: BufferType) {
        val builder = SpannableStringBuilder(text)
        EmojiconHandler.addEmojis(context, builder, mEmojiconSize, mTextStart, mTextLength)
        super.setText(builder, type)
    }

    /**
     * Set the size of emojicon in pixels.
     */
    fun setEmojiconSize(pixels: Int) {
        mEmojiconSize = pixels
    }
}
