package com.python.pydev.analysis.search_index;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.python.pydev.plugin.nature.FileStub2;

import junit.framework.TestCase;

public class SearchIndexQueryTest extends TestCase {

    public void testSearchQuery() throws Exception {
        SearchIndexQuery query = new SearchIndexQuery("my");
        String text = "rara\nmy\nnomyno\nmy";
        IDocument doc = new Document(text);
        IFile f = new FileStub2("stub") {
            @Override
            public long getModificationStamp() {
                return 0;
            }
        };
        AbstractTextSearchResult searchResult = new SearchIndexResult(null);
        query.createMatches(doc, text, query.createStringMatcher(), f, searchResult);
        assertEquals(2, searchResult.getMatchCount());
    }

    public void testSearchQuery2() throws Exception {
        SearchIndexQuery query = new SearchIndexQuery("*my");
        String text = "rara\nmy\nnomyno\nmy";
        IDocument doc = new Document(text);
        IFile f = new FileStub2("stub") {
            @Override
            public long getModificationStamp() {
                return 0;
            }
        };
        AbstractTextSearchResult searchResult = new SearchIndexResult(null);
        query.createMatches(doc, text, query.createStringMatcher(), f, searchResult);
        assertEquals(2, searchResult.getMatchCount());
    }

    public void testSearchQuery3() throws Exception {
        SearchIndexQuery query = new SearchIndexQuery("*my*");
        String text = "rara\nmy\nnomyno\nmy";
        IDocument doc = new Document(text);
        IFile f = new FileStub2("stub") {
            @Override
            public long getModificationStamp() {
                return 0;
            }
        };
        AbstractTextSearchResult searchResult = new SearchIndexResult(null);
        query.createMatches(doc, text, query.createStringMatcher(), f, searchResult);
        assertEquals(3, searchResult.getMatchCount());
    }
}
