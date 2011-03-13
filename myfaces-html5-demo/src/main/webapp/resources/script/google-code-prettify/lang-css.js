/**
 * @fileoverview
 * Registers a language handler for CSS.
 *
 *
 * To use, include prettify.js and this file in your HTML page.
 * Then put your code in an HTML tag like
 *      <pre class="prettyprint lang-css"></pre>
 *
 *
 * http://www.w3.org/TR/CSS21/grammar.html Section G2 defines the lexical
 * grammar.  This scheme does not recognize keywords containing escapes.
 *
 * @author mikesamuel@gmail.com
 */

PR.registerLangHandler(
    PR.createSimpleLexer(
        [
         // The space production <s>
         [PR.PR_PLAIN,       /^[ \t\r\n\f]+/, null, ' \t\r\n\f']
        ],
        [
         // Quoted strings.  <string1> and <string2>
         [PR.PR_STRING,
          /^\"(?:[^\n\r\f\\\"]|\\(?:\r\n?|\n|\f)|\\[\s\S])*\"/, null],
         [PR.PR_STRING,
          /^\'(?:[^\n\r\f\\\']|\\(?:\r\n?|\n|\f)|\\[\s\S])*\'/, null],
         ['lang-css-str', /^url\(([^\)\"\']*)\)/i],
         [PR.PR_KEYWORD,
          /^(?:url|rgb|\!important|@import|@page|@media|@charset|inherit)(?=[^\-\w]|$)/i,
          null],
         // A property name -- an identifier followed by a colon.
         ['lang-css-kw', /^(-?(?:[_a-z]|(?:\\[0-9a-f]+ ?))(?:[_a-z0-9\-]|\\(?:\\[0-9a-f]+ ?))*)\s*:/i],
         // A C style block comment.  The <comment> production.
         [PR.PR_COMMENT, /^\/\*[^*]*\*+(?:[^\/*][^*]*\*+)*\//],
         // Escaping text spans
         [PR.PR_COMMENT, /^(?:<!--|-->)/],
         // A number possibly containing a suffix.
         [PR.PR_LITERAL, /^(?:\d+|\d*\.\d+)(?:%|[a-z]+)?/i],
         // A hex color
         [PR.PR_LITERAL, /^#(?:[0-9a-f]{3}){1,2}/i],
         // An identifier
         [PR.PR_PLAIN,
          /^-?(?:[_a-z]|(?:\\[\da-f]+ ?))(?:[_a-z\d\-]|\\(?:\\[\da-f]+ ?))*/i],
         // A run of punctuation
         [PR.PR_PUNCTUATION, /^[^\s\w\'\"]+/]
        ]),
    ['css']);
PR.registerLangHandler(
    PR.createSimpleLexer([],
        [
         [PR.PR_KEYWORD,
          /^-?(?:[_a-z]|(?:\\[\da-f]+ ?))(?:[_a-z\d\-]|\\(?:\\[\da-f]+ ?))*/i]
        ]),
    ['css-kw']);
PR.registerLangHandler(
    PR.createSimpleLexer([],
        [
         [PR.PR_STRING, /^[^\)\"\']+/]
        ]),
    ['css-str']);
