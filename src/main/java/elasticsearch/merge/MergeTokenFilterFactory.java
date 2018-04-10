package elasticsearch.merge;

import org.apache.lucene.analysis.TokenStream;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.index.IndexSettings;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;

/**
 * Created by nobaksan on 2018. 4. 9..
 */
public class MergeTokenFilterFactory  extends AbstractTokenFilterFactory {

    private String mergeBy = "_";
    public MergeTokenFilterFactory(IndexSettings indexSettings, Environment env, String name, Settings settings) {
        super(indexSettings, name, settings);

//        //merge Symbol
//        String mergeBy = settings.get("merge_by", null);
//        if(Strings.isNullOrEmpty(mergeBy)==false) this.mergeBy = mergeBy;

    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new MergeTokenFilter(tokenStream,mergeBy);
    }
}
