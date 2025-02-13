package com.nilesh.practice.autosuggest.trie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RadixTrieTest {

    @Test
    public void shouldAdd1CityToTrie() {
        RadixTrie radixTrie = new RadixTrie();
        String mumbai = "mumbai";
        radixTrie.build(mumbai.getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        Set<String> results = radixTrie.find("m");
        assertFalse(results.isEmpty(), "There is a mismatch in the child node");
        assertEquals(results.size(), 1, "There is a mismatch in the child node");
        assertTrue(results.contains(mumbai));
    }

    @Test
    public void shouldNotAddDuplicateNamesToTrie() {
        RadixTrie radixTrie = new RadixTrie();
        String mumbai = "mumbai";
        radixTrie.build(mumbai.getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        radixTrie.build(mumbai.getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        Set<String> results = radixTrie.find("m");
        assertFalse(results.isEmpty(), "Search results should not be empty");
        assertTrue(results.contains(mumbai), "First result should be mumbai");
        assertEquals(results.size(), 1, "Search results should be 2.");
    }

    @Test
    public void shouldAdd2CitiesWhichSplitsTheCurrentNodeAndAdds2ChildNodes() {
        RadixTrie radixTrie = new RadixTrie();
        radixTrie.build("mumbai".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        radixTrie.build("mummy".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        Set<String> results = radixTrie.find("m");
        assertFalse(results.isEmpty(), "Search results for 'm' should not be empty");
        assertTrue(results.contains("mumbai"), "First result should be mumbai");
        assertEquals(2, results.size(), "Search results should be 2.");
        assertTrue(results.contains("mummy"), "Second result should be mummy.");
        results = radixTrie.find("mumm");
        assertFalse(results.isEmpty(), "Search results for 'mumm' should not be empty");
        assertTrue(results.contains("mummy"), "First result should be mummy");
        results = radixTrie.find("mumb");
        assertFalse(results.isEmpty(), "Search results for 'mumb' should not be empty");
        assertTrue(results.contains("mumbai"), "First result should be mumbai");
    }

    @Test
    public void shouldAdd2CitiesWhichSplitsTheCurrentNodeAndAdds1ChildNode() {
        RadixTrie radixTrie = new RadixTrie();
        radixTrie.build("mumbai".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        radixTrie.build("mum".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        Set<String> results = radixTrie.find("m");
        assertFalse(results.isEmpty(), "Search results should not be empty");
        assertTrue(results.contains("mumbai"), "First result should be mumbai");
        assertEquals(2, results.size(), "Search results should be 2.");
        assertTrue(results.contains("mum"), "Second result should be mummy.");
    }

    @Test
    public void shouldAddCitiesWhichSplitsTheCurrentNodeWithSingleCharacters() {
        RadixTrie radixTrie = new RadixTrie();
        radixTrie.build("mumbai".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        radixTrie.build("mu".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        radixTrie.build("mum".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        radixTrie.build("mumb".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        Set<String> results = radixTrie.find("m");
        assertFalse(results.isEmpty(), "Search results should not be empty");
        assertEquals(4, results.size(), "Search results should be 4.");
        assertTrue(results.contains("mumbai"), "First result should be mumbai");
        assertTrue(results.contains("mu"), "Second result should be mu.");
        assertTrue(results.contains("mum"), "Second result should be mum.");
        assertTrue(results.contains("mumb"), "Second result should be mumb.");
        // changing the order of insertion
        radixTrie = new RadixTrie();
        radixTrie.build("mu".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        radixTrie.build("mum".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        radixTrie.build("mumb".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        radixTrie.build("mumbai".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        results = radixTrie.find("m");
        assertFalse(results.isEmpty(), "Search results should not be empty");
        assertEquals(4, results.size(), "Search results should be 4.");
        assertTrue(results.contains("mumbai"), "First result should be mumbai");
        assertTrue(results.contains("mu"), "Second result should be mu.");
        assertTrue(results.contains("mum"), "Second result should be mum.");
        assertTrue(results.contains("mumb"), "Second result should be mumb.");
        // changing the order of insertion
        radixTrie = new RadixTrie();
        radixTrie.build("mu".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        radixTrie.build("mumbai".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        radixTrie.build("mum".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        radixTrie.build("mumb".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        results = radixTrie.find("m");
        assertFalse(results.isEmpty(), "Search results should not be empty");
        assertEquals(4, results.size(), "Search results should be 4.");
        assertTrue(results.contains("mumbai"), "First result should be mumbai");
        assertTrue(results.contains("mu"), "Second result should be mu.");
        assertTrue(results.contains("mum"), "Second result should be mum.");
        assertTrue(results.contains("mumb"), "Second result should be mumb.");
        // changing the order of insertion
        radixTrie = new RadixTrie();
        radixTrie.build("mumbai".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        radixTrie.build("mumbais".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        results = radixTrie.find("m");
        assertFalse(results.isEmpty(), "Search results should not be empty");
        assertEquals(2, results.size(), "Search results should be 4.");
        assertTrue(results.contains("mumbai"), "First result should be mumbai");
        assertTrue(results.contains("mumbais"), "Second result should be mumbais.");
        // changing the order of insertion
        radixTrie = new RadixTrie();
        radixTrie.build("mumbais".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        radixTrie.build("mumbai".getBytes(StandardCharsets.US_ASCII), Collections.emptySet());
        results = radixTrie.find("m");
        assertFalse(results.isEmpty(), "Search results should not be empty");
        assertEquals(2, results.size(), "Search results should be 4.");
        assertTrue(results.contains("mumbai"), "First result should be mumbai");
        assertTrue(results.contains("mumbais"), "Second result should be mumbais.");
    }

    @Test
    public void failingTest() {
        RadixTrie radixTrie = new RadixTrie();
        radixTrie.build("Sassenheim".getBytes(StandardCharsets.US_ASCII), Set.of("saneim".getBytes(StandardCharsets.US_ASCII)));
        radixTrie.build("Sassenheim".getBytes(StandardCharsets.US_ASCII), Set.of("aseneim".getBytes(StandardCharsets.US_ASCII)));
        radixTrie.build("Sassenheim".getBytes(StandardCharsets.US_ASCII), Set.of("asneim".getBytes(StandardCharsets.US_ASCII)));
        radixTrie.build("Sassenheim".getBytes(StandardCharsets.US_ASCII), Set.of("sasi".getBytes(StandardCharsets.US_ASCII)));
        radixTrie.build("Sassenheim".getBytes(StandardCharsets.US_ASCII), Set.of("sene".getBytes(StandardCharsets.US_ASCII)));
        radixTrie.build("Sassenheim".getBytes(StandardCharsets.US_ASCII), Set.of("shi".getBytes(StandardCharsets.US_ASCII)));
        radixTrie.build("Sassenheim".getBytes(StandardCharsets.US_ASCII), Set.of("sasseni".getBytes(StandardCharsets.US_ASCII)));

        radixTrie = new RadixTrie();
        radixTrie.build("Sassenheim".getBytes(StandardCharsets.US_ASCII), Set.of("Saneim".getBytes(StandardCharsets.US_ASCII)));
        radixTrie.build("Sassenheim".getBytes(StandardCharsets.US_ASCII), Set.of("aseneim".getBytes(StandardCharsets.US_ASCII)));
        radixTrie.build("Sassenheim".getBytes(StandardCharsets.US_ASCII), Set.of("asneim".getBytes(StandardCharsets.US_ASCII)));
        radixTrie.build("Sassenheim".getBytes(StandardCharsets.US_ASCII), Set.of("Sasi".getBytes(StandardCharsets.US_ASCII)));
        radixTrie.build("Sassenheim".getBytes(StandardCharsets.US_ASCII), Set.of("Sene".getBytes(StandardCharsets.US_ASCII)));
        radixTrie.build("Sassenheim".getBytes(StandardCharsets.US_ASCII), Set.of("shi".getBytes(StandardCharsets.US_ASCII)));
        radixTrie.build("Sassenheim".getBytes(StandardCharsets.US_ASCII), Set.of("Sasseni".getBytes(StandardCharsets.US_ASCII)));
    }
}