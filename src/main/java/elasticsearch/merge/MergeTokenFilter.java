package elasticsearch.merge;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.util.AttributeSource;

import java.io.IOException;

/**
 * Created by nobaksan on 2018. 4. 9..
 */
public class MergeTokenFilter extends TokenFilter {
    private final CharTermAttribute termAtt = (CharTermAttribute)addAttribute(CharTermAttribute.class);
    private final PositionIncrementAttribute posIncrAtt = (PositionIncrementAttribute)addAttribute(PositionIncrementAttribute.class);
    private String tokenSeparator = null;
    private int incrementGap = 100;
    private StringBuilder builder = new StringBuilder();
    private AttributeSource.State previousState = null;
    private boolean recheckPrevious = false;

    public MergeTokenFilter(TokenStream input, String tokenSeparator)
    {
        super(input);
        this.tokenSeparator = (tokenSeparator != null ? tokenSeparator : "_");
    }

    @Override
    public boolean incrementToken() throws IOException
    {
        boolean empty = false;
        this.builder.setLength(0);
        if (this.recheckPrevious)
        {
            restoreState(this.previousState);

            this.builder.append(this.termAtt.buffer(), 0, this.termAtt.length());
            this.recheckPrevious = false;
        }
        while (this.input.incrementToken()) {
            if (this.posIncrAtt.getPositionIncrement() <= this.incrementGap)
            {
                if (this.builder.length() > 0) {
                    this.builder.append(this.tokenSeparator);
                }
                this.builder.append(this.termAtt.buffer(), 0, this.termAtt.length());
            }
            else
            {
                this.recheckPrevious = true;
                this.previousState = captureState();
            }
        }
        if (this.builder.length() > 0)
        {
            this.termAtt.setEmpty().append(this.builder);
            if (!this.recheckPrevious) {
                empty = true;
            }
        }
        return empty;
    }
}
