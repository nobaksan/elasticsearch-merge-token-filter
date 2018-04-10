package org.elasticsearch.index.analysis;


import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.env.Environment;
import org.elasticsearch.plugin.analysis.starstory.AnalysisMergeFilterPlugin;
import org.elasticsearch.test.ESTestCase;
import org.elasticsearch.test.ESTokenStreamTestCase;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.elasticsearch.index.analysis.AnalysisTestsHelper.createTestAnalysisFromSettings;

/**
 * Created by nobaksan on 2018. 4. 9..
 */
public class MergeFilterAnalyzerTest extends ESTokenStreamTestCase {

    @Test
    public void testMergeTokenFilter() throws IOException {
        Settings settings = Settings.builder()
                .put(Environment.PATH_HOME_SETTING.getKey(), createTempDir().toString())
                .build();
        ESTestCase.TestAnalysis analysis = createTestAnalysisFromSettings(settings, new AnalysisMergeFilterPlugin());

        TokenFilterFactory tokenFilter = analysis.tokenFilter.get("mergeFilter");
        String source = "아이폰 삼성 전자 아이폰";
        String[] expected = new String[]{"아이폰_삼성_전자_아이폰" };
        Tokenizer tokenizer = new StandardTokenizer();
        tokenizer.setReader(new StringReader(source));
        assertTokenStreamContents(tokenFilter.create(tokenizer), expected);
    }
}
