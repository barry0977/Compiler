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

varDefUnit: Identifier ('=' expression)?;
varDef: type varDefUnit (',' varDefUnit)* ';';
funcDef: returntype Identifier '(' (type Identifier)? (',' type Identifier)*')'suite;
construct: Identifier '(' ')' suite;
classDef: Class Identifier '{'(varDef|funcDef|construct)*'}'';';

statement
    : suite                                                 #block
    | varDef                                                #vardefStmt
    | If '(' expression ')' trueStmt=statement
        (Else falseStmt=statement)?                         #ifStmt
    | While '(' expression ')' statement                    #whileStmt
    | For '('initStmt=statement cond_=expression? ';' next_=expression? ')'
         statement                                          #forStmt
    | Break ';'                                             #breakStmt
    | Continue ';'                                          #continueStmt
    | Return expression? ';'                                #returnStmt
    | expression ';'                                        #pureExprStmt
    | ';'                                                   #emptyStmt
    ;


expression
    :New typename ('['expression?']')+ constArray? #newArrayExpr
    |New typename ('('')')? #newVarExpr
    |expression '('(expression (',' expression)*)? ')' #funcExpr
    |expression '[' expression ']' #arrayExpr
    |expression op=Fullstop Identifier #memberExpr
    |expression op=(Increment|Decrement) #sufExpr
    |<assoc=right>op=(Increment|Decrement) expression #preExpr
    |<assoc=right>op=(Not|Tilde|Minus) expression #unaryExpr
    |expression op=(Multiply|Divide|Mod) expression #binaryExpr
    |expression op=(Plus|Minus) expression #binaryExpr
    |expression op=(LeftShift|RightShift) expression #binaryExpr
    |expression op=(Less|LessEqual|Greater|GreaterEqual) expression #binaryExpr
    |expression op=(Equal|NotEqual) expression #binaryExpr
    |expression op=And expression #binaryExpr
    |expression op=Caret expression #binaryExpr
    |expression op=Or expression #binaryExpr
    |expression op=AndAnd expression #binaryExpr
    |expression op=OrOr expression #binaryExpr
    |<assoc=right>expression '?' expression ':' expression #conditionExpr
    |<assoc=right> expression op=Assign expression #assignExpr
    |'(' expression ')' #parenExpr
    |fstring #fStringExpr
    |primary #basicExpr
    ;

primary
    :Identifier
    |const
    |This
    ;

BasicFString:'f"'(FormatChar|Escape|'$$')*'"';
fstring:(FStringFront expression (FStringMid expression)* FStringEnd)|BasicFString;
FStringFront: 'f"'(FormatChar|Escape|'$$')*'$';
FStringMid:'$'(FormatChar|Escape|'$$')*'$';
FStringEnd:'$'(FormatChar|Escape|'$$')*'"';


const:True|False|DecimalInteger|ConstString|Null|constArray;
constArray:('{' const (','  const )* '}')|emptyArrayUnit;//要设置成parser，否则不能忽略空白
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

fragment VisibleChar: [\u0020-\u0021\u0023-\u005b\u005d-\u007e];//所有可示字符,除去" \
fragment FormatChar: [\u0020-\u0021\u0023\u0025-\u005b\u005d-\u007e];//除去$ " \的可示字符
fragment Escape: '\\n'|'\\"'|'\\\\';//转义字符
emptyArrayUnit: '{''}';

Whitespace: [ \t\n\r]+ -> skip;

BlockComment: '/*' .*? '*/'-> skip;

LineComment: '//' ~[\r\n]* -> skip;