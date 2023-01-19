/*==============================================================================
 * OntoqlLexer.jflex -- To be used with the jflex lexical scanner
 * generator at www.jflex.de
 * Copyright (C) 2002 John Wheeler
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 ==============================================================================*/
package fr.ensma.lisi.ontoqlplus.ontoqlWorkbench.syntax;

import java.io.*;

%%

%public
%class OntoqlLexer
%implements Lexer
%function getNextToken
%type Token
%caseless

%{
    private int lastToken;
    private int nextState = YYINITIAL;

    public void reset(java.io.Reader reader, int yyline, int yychar, int yycolumn) throws IOException{
        yyreset(reader);
        this.yyline = yyline;
        this.yychar = yychar;
        this.yycolumn = yycolumn;
    }
%}

%line
%char
%full

AnyNonSeparator=([^\t\f\r\n\ \(\)\,])
Plain=[^\r\n]

BLANK=([ ])
TAB=([\t])
FF=([\f])
CR=([\r])
LF=([\n])
EOL=({CR}|{LF}|{CR}{LF})
WhiteSpace=({BLANK}|{TAB}|{FF}|{EOL})
MultipleWhiteSpace=({WhiteSpace}+)

AnyStrChar=([^\'\n\r\\])
UnclosedString=([\']({AnyStrChar})*)
String=({UnclosedString}[\'])

AnsiKeywords=select|from|where|group|by|having|order|
			 union|intersect|except|
			 distinct|all|as|case|when|then|else|end|
			 on|inner|join|left|outer|right|unnest|typeof|only|
			 between|in|is|not|null|like|of|
			 and|or|
			 desc|asc|
			 create|under|language|none|
			 insert|into|values|
			 update|set|
			 delete|
			 descriptor|properties|
			 entity|attribute|
			 using|namespace|
			 prefix|optional|filter|
			 preferring

NonAnsiKeywords=where //...

ErrorKeyword=({AnyNonSeparator}+)

%%

<YYINITIAL> {UnclosedString} {
    lastToken = OntoqlToken.STRING_LITERAL;
    String text = yytext();
    OntoqlToken t = new OntoqlToken(lastToken, text, yyline, yychar, yychar+text.length(), nextState);
    return t;
}
<YYINITIAL> {String} {
    lastToken = OntoqlToken.STRING_LITERAL;
    String text = yytext();
    OntoqlToken t = new OntoqlToken(lastToken, text, yyline, yychar, yychar+text.length(), nextState);
    return t;
}
<YYINITIAL> {AnsiKeywords} {
    lastToken = OntoqlToken.KEYWORD;
    String text = yytext();
    OntoqlToken t = new OntoqlToken(lastToken, text, yyline, yychar, yychar+text.length(), nextState);
    return t;
}
<YYINITIAL> {NonAnsiKeywords} {
    lastToken = OntoqlToken.KEYWORD;
    String text = yytext();
    OntoqlToken t = new OntoqlToken(lastToken, text, yyline, yychar, yychar+text.length(), nextState);
    return t;
}
<YYINITIAL> {ErrorKeyword} {
    lastToken = OntoqlToken.PLAIN;
    String text = yytext();
    OntoqlToken t = new OntoqlToken(lastToken, text, yyline, yychar, yychar+text.length(), nextState);
    return t;
}
<YYINITIAL> {Plain} {
    lastToken = OntoqlToken.PLAIN;
    String text = yytext();
    OntoqlToken t = new OntoqlToken(lastToken, text, yyline, yychar, yychar+text.length(), nextState);
    return t;
}
<YYINITIAL>{MultipleWhiteSpace} {}