/**
 * @fileoverview
 * Registers a language handler for Lua.
 *
 *
 * To use, include prettify.js and this file in your HTML page.
 * Then put your code in an HTML tag like
 *      <pre class="prettyprint lang-lua">(my Lua code)</pre>
 *
 *
 * I used http://www.lua.org/manual/5.1/manual.html#2.1
 * Because of the long-bracket concept used in strings and comments, Lua does
 * not have a regular lexical grammar, but luckily it fits within the space
 * of irregular grammars supported by javascript regular expressions.
 *
 * @author mikesamuel@gmail.com
 */

PR.registerLangHandler(
    PR.createSimpleLexer(
        [
         // Whitespace
         [PR.PR_PLAIN,       /^[\t\n\r \xA0]+/, null, '\t\n\r \xA0'],
         // A double or single quoted, possibly multi-line, string.
         [PR.PR_STRING,      /^(?:\"(?:[^\"\\]|\\[\s\S])*(?:\"|$)|\'(?:[^\'\\]|\\[\s\S])*(?:\'|$))/, null, '"\'']
        ],
        [
         // A comment is either a line comment that starts with two dashes, or
         // two dashes preceding a long bracketed block.
         [PR.PR_COMMENT, /^--(?:\[(=*)\[[\s\S]*?(?:\]\1\]|$)|[^\r\n]*)/],
         // A long bracketed block not preceded by -- is a string.
         [PR.PR_STRING,  /^\[(=*)\[[\s\S]*?(?:\]\1\]|$)/],
         [PR.PR_KEYWORD, /^(?:and|break|do|else|elseif|end|false|for|function|if|in|local|nil|not|or|repeat|return|then|true|until|while)\b/, null],
         // A number is a hex integer literal, a decimal real literal, or in
         // scientific notation.
         [PR.PR_LITERAL,
          /^[+-]?(?:0x[\da-f]+|(?:(?:\.\d+|\d+(?:\.\d*)?)(?:e[+\-]?\d+)?))/i],
         // An identifier
         [PR.PR_PLAIN, /^[a-z_]\w*/i],
         // A run of punctuation
         [PR.PR_PUNCTUATION, /^[^\w\t\n\r \xA0][^\w\t\n\r \xA0\"\'\-\+=]*/]
        ]),
    ['lua']);
