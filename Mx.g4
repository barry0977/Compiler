grammar Mx;

program:(funcDef|classDef|varDef)*;

suite: '{'statement* '}';

basictype
    :Bool
    |Int
    |String
    ;

typename
    :basictype
    |Identifier//自己创建的类名
    ;

type: typename ('['']')*;

returntype: type|Void;

varDef: type Identifier ('=' expression)? (',' Identifier ('=' expression)?)* ';';
funcDef: returntype Identifier '(' (type Identifier)? (',' type Identifier)*')'suite;
construct: Identifier '(' ')'suite;
classDef: Class Identifier '{'(varDef|funcDef';'|construct';')*'}'';';

statement
    : suite                                                 #block
    | varDef                                                #vardefStmt
    | If '(' expression ')' trueStmt=statement
        (Else falseStmt=statement)?                         #ifStmt
    | While '(' expression ')' statement                    #whileStmt
    | For '('initStmt=statement expression ';' expression ')'
         statement                                          #forStmt
    | Break ';'                                             #breakStmt
    | Continue ';'                                          #continueStmt
    | Return expression? ';'                                #returnStmt
    | expression ';'                                        #pureExprStmt
    | ';'                                                   #emptyStmt
    ;


expression
    :New type ('('')')?
    |New type ('['DecimalInteger?']')+ ConstArray?
    |expression op=Fullstop Identifier
    |expression op=(Increment|Decrement)
    |<assoc=right>op=(Increment|Decrement) expression
    |<assoc=right>op=(Not|Tilde|Minus) expression
    |expression op=(Multiply|Divide|Mod) expression
    |expression op=(Plus|Minus) expression
    |expression op=(LeftShift|RightShift) expression
    |expression op=(Less|LessEqual|Greater|GreaterEqual) expression
    |expression op=(Equal|NotEqual) expression
    |expression op=And expression
    |expression op=Caret expression
    |expression op=Or expression
    |expression op=AndAnd expression
    |expression op=OrOr expression
    |<assoc=right>expression '?' expression ':' expression
    |<assoc=right> expression op=Assign expression
    |primary
    ;

primary
    :Identifier
    |Const
    |This
    ;

ConstArray :'{'Const (',' Const)* '}'|EmptyArrayUnit;

Const:True|False|DecimalInteger|ConstString|Null|ConstArray;

ConstString:'"' (VisibleChar|Escape)* '"';

Void : 'void';
Bool : 'bool';
Int : 'int';
String : 'string';
New : 'new';
Class : 'class';
Null : 'null';
True : 'true';
False : 'false';
This : 'this';
If : 'if';
Else :'else';
For : 'for';
While :'while';
Break :'break';
Continue :'continue';
Return : 'return';

LeftParen : '(';
RightParen : ')';
LeftBracket : '[';
RightBracket : ']';
LeftBrace : '{';
RightBrace : '}';

Less : '<';
LessEqual : '<=';
Greater : '>';
GreaterEqual : '>=';
LeftShift : '<<';
RightShift : '>>';

Plus : '+';
Minus : '-';
Multiply : '*';
Divide : '/';
Mod : '%';
Increment: '++';
Decrement: '--';
And : '&';
Or : '|';
AndAnd : '&&';
OrOr : '||';
Caret : '^';
Not : '!';
Tilde : '~';

Question : '?';
Colon : ':';
Semi : ';';
Comma : ',';
Quotation: '"';
Fullstop : '.';

Assign : '=';
Equal : '==';
NotEqual : '!=';

Identifier
    : [a-zA-Z] [a-zA-Z_0-9]*
    ;

DecimalInteger
    :[1-9][0-9]*
    |'0'
    ;

fragment VisibleChar: [\u0020-\u007e];//所有可示字符
fragment Escape: '\\n'|'\\"'|'\\\\';//转义字符
fragment EmptyArrayUnit: '{''}';

Whitespace
    :   [ \t\n\r]+
        -> skip
    ;


BlockComment
    :   '/*' .*? '*/'
        -> skip
    ;

LineComment
    :   '//' ~[\r\n]*
        -> skip
    ;