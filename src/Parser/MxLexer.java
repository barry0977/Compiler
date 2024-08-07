// Generated from E:/java/Compiler/src/Mx.g4 by ANTLR 4.13.1
package Parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class MxLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BasicFString=1, FStringFront=2, FStringMid=3, FStringEnd=4, ConstString=5, 
		Void=6, Bool=7, Int=8, String=9, New=10, Class=11, Null=12, True=13, False=14, 
		This=15, If=16, Else=17, For=18, While=19, Break=20, Continue=21, Return=22, 
		LeftParen=23, RightParen=24, LeftBracket=25, RightBracket=26, LeftBrace=27, 
		RightBrace=28, Less=29, LessEqual=30, Greater=31, GreaterEqual=32, LeftShift=33, 
		RightShift=34, Plus=35, Minus=36, Multiply=37, Divide=38, Mod=39, Increment=40, 
		Decrement=41, And=42, Or=43, AndAnd=44, OrOr=45, Caret=46, Not=47, Tilde=48, 
		Question=49, Colon=50, Semi=51, Comma=52, Quotation=53, Fullstop=54, Assign=55, 
		Equal=56, NotEqual=57, Identifier=58, DecimalInteger=59, EmptyArrayUnit=60, 
		Whitespace=61, BlockComment=62, LineComment=63;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"BasicFString", "FStringFront", "FStringMid", "FStringEnd", "ConstString", 
			"Void", "Bool", "Int", "String", "New", "Class", "Null", "True", "False", 
			"This", "If", "Else", "For", "While", "Break", "Continue", "Return", 
			"LeftParen", "RightParen", "LeftBracket", "RightBracket", "LeftBrace", 
			"RightBrace", "Less", "LessEqual", "Greater", "GreaterEqual", "LeftShift", 
			"RightShift", "Plus", "Minus", "Multiply", "Divide", "Mod", "Increment", 
			"Decrement", "And", "Or", "AndAnd", "OrOr", "Caret", "Not", "Tilde", 
			"Question", "Colon", "Semi", "Comma", "Quotation", "Fullstop", "Assign", 
			"Equal", "NotEqual", "Identifier", "DecimalInteger", "VisibleChar", "FormatChar", 
			"Escape", "EmptyArrayUnit", "Whitespace", "BlockComment", "LineComment"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, "'void'", "'bool'", "'int'", "'string'", 
			"'new'", "'class'", "'null'", "'true'", "'false'", "'this'", "'if'", 
			"'else'", "'for'", "'while'", "'break'", "'continue'", "'return'", "'('", 
			"')'", "'['", "']'", "'{'", "'}'", "'<'", "'<='", "'>'", "'>='", "'<<'", 
			"'>>'", "'+'", "'-'", "'*'", "'/'", "'%'", "'++'", "'--'", "'&'", "'|'", 
			"'&&'", "'||'", "'^'", "'!'", "'~'", "'?'", "':'", "';'", "','", "'\"'", 
			"'.'", "'='", "'=='", "'!='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BasicFString", "FStringFront", "FStringMid", "FStringEnd", "ConstString", 
			"Void", "Bool", "Int", "String", "New", "Class", "Null", "True", "False", 
			"This", "If", "Else", "For", "While", "Break", "Continue", "Return", 
			"LeftParen", "RightParen", "LeftBracket", "RightBracket", "LeftBrace", 
			"RightBrace", "Less", "LessEqual", "Greater", "GreaterEqual", "LeftShift", 
			"RightShift", "Plus", "Minus", "Multiply", "Divide", "Mod", "Increment", 
			"Decrement", "And", "Or", "AndAnd", "OrOr", "Caret", "Not", "Tilde", 
			"Question", "Colon", "Semi", "Comma", "Quotation", "Fullstop", "Assign", 
			"Equal", "NotEqual", "Identifier", "DecimalInteger", "EmptyArrayUnit", 
			"Whitespace", "BlockComment", "LineComment"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public MxLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Mx.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000?\u01af\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007"+
		"!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007"+
		"&\u0002\'\u0007\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007"+
		"+\u0002,\u0007,\u0002-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u0007"+
		"0\u00021\u00071\u00022\u00072\u00023\u00073\u00024\u00074\u00025\u0007"+
		"5\u00026\u00076\u00027\u00077\u00028\u00078\u00029\u00079\u0002:\u0007"+
		":\u0002;\u0007;\u0002<\u0007<\u0002=\u0007=\u0002>\u0007>\u0002?\u0007"+
		"?\u0002@\u0007@\u0002A\u0007A\u0001\u0000\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000\u008d\b\u0000\n"+
		"\u0000\f\u0000\u0090\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005"+
		"\u0001\u009b\b\u0001\n\u0001\f\u0001\u009e\t\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002"+
		"\u00a7\b\u0002\n\u0002\f\u0002\u00aa\t\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003\u00b3"+
		"\b\u0003\n\u0003\f\u0003\u00b6\t\u0003\u0001\u0003\u0001\u0003\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0005\u0004\u00bd\b\u0004\n\u0004\f\u0004\u00c0"+
		"\t\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018"+
		"\u0001\u0018\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001b"+
		"\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001\u001f\u0001 \u0001"+
		" \u0001 \u0001!\u0001!\u0001!\u0001\"\u0001\"\u0001#\u0001#\u0001$\u0001"+
		"$\u0001%\u0001%\u0001&\u0001&\u0001\'\u0001\'\u0001\'\u0001(\u0001(\u0001"+
		"(\u0001)\u0001)\u0001*\u0001*\u0001+\u0001+\u0001+\u0001,\u0001,\u0001"+
		",\u0001-\u0001-\u0001.\u0001.\u0001/\u0001/\u00010\u00010\u00011\u0001"+
		"1\u00012\u00012\u00013\u00013\u00014\u00014\u00015\u00015\u00016\u0001"+
		"6\u00017\u00017\u00017\u00018\u00018\u00018\u00019\u00019\u00059\u0172"+
		"\b9\n9\f9\u0175\t9\u0001:\u0001:\u0005:\u0179\b:\n:\f:\u017c\t:\u0001"+
		":\u0003:\u017f\b:\u0001;\u0001;\u0001<\u0001<\u0001=\u0001=\u0001=\u0001"+
		"=\u0001=\u0001=\u0003=\u018b\b=\u0001>\u0001>\u0001>\u0001?\u0004?\u0191"+
		"\b?\u000b?\f?\u0192\u0001?\u0001?\u0001@\u0001@\u0001@\u0001@\u0005@\u019b"+
		"\b@\n@\f@\u019e\t@\u0001@\u0001@\u0001@\u0001@\u0001@\u0001A\u0001A\u0001"+
		"A\u0001A\u0005A\u01a9\bA\nA\fA\u01ac\tA\u0001A\u0001A\u0001\u019c\u0000"+
		"B\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006"+
		"\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e"+
		"\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017"+
		"/\u00181\u00193\u001a5\u001b7\u001c9\u001d;\u001e=\u001f? A!C\"E#G$I%"+
		"K&M\'O(Q)S*U+W,Y-[.]/_0a1c2e3g4i5k6m7o8q9s:u;w\u0000y\u0000{\u0000}<\u007f"+
		"=\u0081>\u0083?\u0001\u0000\b\u0002\u0000AZaz\u0004\u000009AZ__az\u0001"+
		"\u000019\u0001\u000009\u0003\u0000 !#[]~\u0004\u0000 !##%[]~\u0003\u0000"+
		"\t\n\r\r  \u0002\u0000\n\n\r\r\u01c1\u0000\u0001\u0001\u0000\u0000\u0000"+
		"\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000"+
		"\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000"+
		"\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f"+
		"\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013"+
		"\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017"+
		"\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b"+
		"\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f"+
		"\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000"+
		"\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000\u0000"+
		"\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000\u0000"+
		"-\u0001\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000\u00001\u0001"+
		"\u0000\u0000\u0000\u00003\u0001\u0000\u0000\u0000\u00005\u0001\u0000\u0000"+
		"\u0000\u00007\u0001\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000\u0000"+
		";\u0001\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000\u0000?\u0001"+
		"\u0000\u0000\u0000\u0000A\u0001\u0000\u0000\u0000\u0000C\u0001\u0000\u0000"+
		"\u0000\u0000E\u0001\u0000\u0000\u0000\u0000G\u0001\u0000\u0000\u0000\u0000"+
		"I\u0001\u0000\u0000\u0000\u0000K\u0001\u0000\u0000\u0000\u0000M\u0001"+
		"\u0000\u0000\u0000\u0000O\u0001\u0000\u0000\u0000\u0000Q\u0001\u0000\u0000"+
		"\u0000\u0000S\u0001\u0000\u0000\u0000\u0000U\u0001\u0000\u0000\u0000\u0000"+
		"W\u0001\u0000\u0000\u0000\u0000Y\u0001\u0000\u0000\u0000\u0000[\u0001"+
		"\u0000\u0000\u0000\u0000]\u0001\u0000\u0000\u0000\u0000_\u0001\u0000\u0000"+
		"\u0000\u0000a\u0001\u0000\u0000\u0000\u0000c\u0001\u0000\u0000\u0000\u0000"+
		"e\u0001\u0000\u0000\u0000\u0000g\u0001\u0000\u0000\u0000\u0000i\u0001"+
		"\u0000\u0000\u0000\u0000k\u0001\u0000\u0000\u0000\u0000m\u0001\u0000\u0000"+
		"\u0000\u0000o\u0001\u0000\u0000\u0000\u0000q\u0001\u0000\u0000\u0000\u0000"+
		"s\u0001\u0000\u0000\u0000\u0000u\u0001\u0000\u0000\u0000\u0000}\u0001"+
		"\u0000\u0000\u0000\u0000\u007f\u0001\u0000\u0000\u0000\u0000\u0081\u0001"+
		"\u0000\u0000\u0000\u0000\u0083\u0001\u0000\u0000\u0000\u0001\u0085\u0001"+
		"\u0000\u0000\u0000\u0003\u0093\u0001\u0000\u0000\u0000\u0005\u00a1\u0001"+
		"\u0000\u0000\u0000\u0007\u00ad\u0001\u0000\u0000\u0000\t\u00b9\u0001\u0000"+
		"\u0000\u0000\u000b\u00c3\u0001\u0000\u0000\u0000\r\u00c8\u0001\u0000\u0000"+
		"\u0000\u000f\u00cd\u0001\u0000\u0000\u0000\u0011\u00d1\u0001\u0000\u0000"+
		"\u0000\u0013\u00d8\u0001\u0000\u0000\u0000\u0015\u00dc\u0001\u0000\u0000"+
		"\u0000\u0017\u00e2\u0001\u0000\u0000\u0000\u0019\u00e7\u0001\u0000\u0000"+
		"\u0000\u001b\u00ec\u0001\u0000\u0000\u0000\u001d\u00f2\u0001\u0000\u0000"+
		"\u0000\u001f\u00f7\u0001\u0000\u0000\u0000!\u00fa\u0001\u0000\u0000\u0000"+
		"#\u00ff\u0001\u0000\u0000\u0000%\u0103\u0001\u0000\u0000\u0000\'\u0109"+
		"\u0001\u0000\u0000\u0000)\u010f\u0001\u0000\u0000\u0000+\u0118\u0001\u0000"+
		"\u0000\u0000-\u011f\u0001\u0000\u0000\u0000/\u0121\u0001\u0000\u0000\u0000"+
		"1\u0123\u0001\u0000\u0000\u00003\u0125\u0001\u0000\u0000\u00005\u0127"+
		"\u0001\u0000\u0000\u00007\u0129\u0001\u0000\u0000\u00009\u012b\u0001\u0000"+
		"\u0000\u0000;\u012d\u0001\u0000\u0000\u0000=\u0130\u0001\u0000\u0000\u0000"+
		"?\u0132\u0001\u0000\u0000\u0000A\u0135\u0001\u0000\u0000\u0000C\u0138"+
		"\u0001\u0000\u0000\u0000E\u013b\u0001\u0000\u0000\u0000G\u013d\u0001\u0000"+
		"\u0000\u0000I\u013f\u0001\u0000\u0000\u0000K\u0141\u0001\u0000\u0000\u0000"+
		"M\u0143\u0001\u0000\u0000\u0000O\u0145\u0001\u0000\u0000\u0000Q\u0148"+
		"\u0001\u0000\u0000\u0000S\u014b\u0001\u0000\u0000\u0000U\u014d\u0001\u0000"+
		"\u0000\u0000W\u014f\u0001\u0000\u0000\u0000Y\u0152\u0001\u0000\u0000\u0000"+
		"[\u0155\u0001\u0000\u0000\u0000]\u0157\u0001\u0000\u0000\u0000_\u0159"+
		"\u0001\u0000\u0000\u0000a\u015b\u0001\u0000\u0000\u0000c\u015d\u0001\u0000"+
		"\u0000\u0000e\u015f\u0001\u0000\u0000\u0000g\u0161\u0001\u0000\u0000\u0000"+
		"i\u0163\u0001\u0000\u0000\u0000k\u0165\u0001\u0000\u0000\u0000m\u0167"+
		"\u0001\u0000\u0000\u0000o\u0169\u0001\u0000\u0000\u0000q\u016c\u0001\u0000"+
		"\u0000\u0000s\u016f\u0001\u0000\u0000\u0000u\u017e\u0001\u0000\u0000\u0000"+
		"w\u0180\u0001\u0000\u0000\u0000y\u0182\u0001\u0000\u0000\u0000{\u018a"+
		"\u0001\u0000\u0000\u0000}\u018c\u0001\u0000\u0000\u0000\u007f\u0190\u0001"+
		"\u0000\u0000\u0000\u0081\u0196\u0001\u0000\u0000\u0000\u0083\u01a4\u0001"+
		"\u0000\u0000\u0000\u0085\u0086\u0005f\u0000\u0000\u0086\u0087\u0005\""+
		"\u0000\u0000\u0087\u008e\u0001\u0000\u0000\u0000\u0088\u008d\u0003y<\u0000"+
		"\u0089\u008d\u0003{=\u0000\u008a\u008b\u0005$\u0000\u0000\u008b\u008d"+
		"\u0005$\u0000\u0000\u008c\u0088\u0001\u0000\u0000\u0000\u008c\u0089\u0001"+
		"\u0000\u0000\u0000\u008c\u008a\u0001\u0000\u0000\u0000\u008d\u0090\u0001"+
		"\u0000\u0000\u0000\u008e\u008c\u0001\u0000\u0000\u0000\u008e\u008f\u0001"+
		"\u0000\u0000\u0000\u008f\u0091\u0001\u0000\u0000\u0000\u0090\u008e\u0001"+
		"\u0000\u0000\u0000\u0091\u0092\u0005\"\u0000\u0000\u0092\u0002\u0001\u0000"+
		"\u0000\u0000\u0093\u0094\u0005f\u0000\u0000\u0094\u0095\u0005\"\u0000"+
		"\u0000\u0095\u009c\u0001\u0000\u0000\u0000\u0096\u009b\u0003y<\u0000\u0097"+
		"\u009b\u0003{=\u0000\u0098\u0099\u0005$\u0000\u0000\u0099\u009b\u0005"+
		"$\u0000\u0000\u009a\u0096\u0001\u0000\u0000\u0000\u009a\u0097\u0001\u0000"+
		"\u0000\u0000\u009a\u0098\u0001\u0000\u0000\u0000\u009b\u009e\u0001\u0000"+
		"\u0000\u0000\u009c\u009a\u0001\u0000\u0000\u0000\u009c\u009d\u0001\u0000"+
		"\u0000\u0000\u009d\u009f\u0001\u0000\u0000\u0000\u009e\u009c\u0001\u0000"+
		"\u0000\u0000\u009f\u00a0\u0005$\u0000\u0000\u00a0\u0004\u0001\u0000\u0000"+
		"\u0000\u00a1\u00a8\u0005$\u0000\u0000\u00a2\u00a7\u0003y<\u0000\u00a3"+
		"\u00a7\u0003{=\u0000\u00a4\u00a5\u0005$\u0000\u0000\u00a5\u00a7\u0005"+
		"$\u0000\u0000\u00a6\u00a2\u0001\u0000\u0000\u0000\u00a6\u00a3\u0001\u0000"+
		"\u0000\u0000\u00a6\u00a4\u0001\u0000\u0000\u0000\u00a7\u00aa\u0001\u0000"+
		"\u0000\u0000\u00a8\u00a6\u0001\u0000\u0000\u0000\u00a8\u00a9\u0001\u0000"+
		"\u0000\u0000\u00a9\u00ab\u0001\u0000\u0000\u0000\u00aa\u00a8\u0001\u0000"+
		"\u0000\u0000\u00ab\u00ac\u0005$\u0000\u0000\u00ac\u0006\u0001\u0000\u0000"+
		"\u0000\u00ad\u00b4\u0005$\u0000\u0000\u00ae\u00b3\u0003y<\u0000\u00af"+
		"\u00b3\u0003{=\u0000\u00b0\u00b1\u0005$\u0000\u0000\u00b1\u00b3\u0005"+
		"$\u0000\u0000\u00b2\u00ae\u0001\u0000\u0000\u0000\u00b2\u00af\u0001\u0000"+
		"\u0000\u0000\u00b2\u00b0\u0001\u0000\u0000\u0000\u00b3\u00b6\u0001\u0000"+
		"\u0000\u0000\u00b4\u00b2\u0001\u0000\u0000\u0000\u00b4\u00b5\u0001\u0000"+
		"\u0000\u0000\u00b5\u00b7\u0001\u0000\u0000\u0000\u00b6\u00b4\u0001\u0000"+
		"\u0000\u0000\u00b7\u00b8\u0005\"\u0000\u0000\u00b8\b\u0001\u0000\u0000"+
		"\u0000\u00b9\u00be\u0005\"\u0000\u0000\u00ba\u00bd\u0003w;\u0000\u00bb"+
		"\u00bd\u0003{=\u0000\u00bc\u00ba\u0001\u0000\u0000\u0000\u00bc\u00bb\u0001"+
		"\u0000\u0000\u0000\u00bd\u00c0\u0001\u0000\u0000\u0000\u00be\u00bc\u0001"+
		"\u0000\u0000\u0000\u00be\u00bf\u0001\u0000\u0000\u0000\u00bf\u00c1\u0001"+
		"\u0000\u0000\u0000\u00c0\u00be\u0001\u0000\u0000\u0000\u00c1\u00c2\u0005"+
		"\"\u0000\u0000\u00c2\n\u0001\u0000\u0000\u0000\u00c3\u00c4\u0005v\u0000"+
		"\u0000\u00c4\u00c5\u0005o\u0000\u0000\u00c5\u00c6\u0005i\u0000\u0000\u00c6"+
		"\u00c7\u0005d\u0000\u0000\u00c7\f\u0001\u0000\u0000\u0000\u00c8\u00c9"+
		"\u0005b\u0000\u0000\u00c9\u00ca\u0005o\u0000\u0000\u00ca\u00cb\u0005o"+
		"\u0000\u0000\u00cb\u00cc\u0005l\u0000\u0000\u00cc\u000e\u0001\u0000\u0000"+
		"\u0000\u00cd\u00ce\u0005i\u0000\u0000\u00ce\u00cf\u0005n\u0000\u0000\u00cf"+
		"\u00d0\u0005t\u0000\u0000\u00d0\u0010\u0001\u0000\u0000\u0000\u00d1\u00d2"+
		"\u0005s\u0000\u0000\u00d2\u00d3\u0005t\u0000\u0000\u00d3\u00d4\u0005r"+
		"\u0000\u0000\u00d4\u00d5\u0005i\u0000\u0000\u00d5\u00d6\u0005n\u0000\u0000"+
		"\u00d6\u00d7\u0005g\u0000\u0000\u00d7\u0012\u0001\u0000\u0000\u0000\u00d8"+
		"\u00d9\u0005n\u0000\u0000\u00d9\u00da\u0005e\u0000\u0000\u00da\u00db\u0005"+
		"w\u0000\u0000\u00db\u0014\u0001\u0000\u0000\u0000\u00dc\u00dd\u0005c\u0000"+
		"\u0000\u00dd\u00de\u0005l\u0000\u0000\u00de\u00df\u0005a\u0000\u0000\u00df"+
		"\u00e0\u0005s\u0000\u0000\u00e0\u00e1\u0005s\u0000\u0000\u00e1\u0016\u0001"+
		"\u0000\u0000\u0000\u00e2\u00e3\u0005n\u0000\u0000\u00e3\u00e4\u0005u\u0000"+
		"\u0000\u00e4\u00e5\u0005l\u0000\u0000\u00e5\u00e6\u0005l\u0000\u0000\u00e6"+
		"\u0018\u0001\u0000\u0000\u0000\u00e7\u00e8\u0005t\u0000\u0000\u00e8\u00e9"+
		"\u0005r\u0000\u0000\u00e9\u00ea\u0005u\u0000\u0000\u00ea\u00eb\u0005e"+
		"\u0000\u0000\u00eb\u001a\u0001\u0000\u0000\u0000\u00ec\u00ed\u0005f\u0000"+
		"\u0000\u00ed\u00ee\u0005a\u0000\u0000\u00ee\u00ef\u0005l\u0000\u0000\u00ef"+
		"\u00f0\u0005s\u0000\u0000\u00f0\u00f1\u0005e\u0000\u0000\u00f1\u001c\u0001"+
		"\u0000\u0000\u0000\u00f2\u00f3\u0005t\u0000\u0000\u00f3\u00f4\u0005h\u0000"+
		"\u0000\u00f4\u00f5\u0005i\u0000\u0000\u00f5\u00f6\u0005s\u0000\u0000\u00f6"+
		"\u001e\u0001\u0000\u0000\u0000\u00f7\u00f8\u0005i\u0000\u0000\u00f8\u00f9"+
		"\u0005f\u0000\u0000\u00f9 \u0001\u0000\u0000\u0000\u00fa\u00fb\u0005e"+
		"\u0000\u0000\u00fb\u00fc\u0005l\u0000\u0000\u00fc\u00fd\u0005s\u0000\u0000"+
		"\u00fd\u00fe\u0005e\u0000\u0000\u00fe\"\u0001\u0000\u0000\u0000\u00ff"+
		"\u0100\u0005f\u0000\u0000\u0100\u0101\u0005o\u0000\u0000\u0101\u0102\u0005"+
		"r\u0000\u0000\u0102$\u0001\u0000\u0000\u0000\u0103\u0104\u0005w\u0000"+
		"\u0000\u0104\u0105\u0005h\u0000\u0000\u0105\u0106\u0005i\u0000\u0000\u0106"+
		"\u0107\u0005l\u0000\u0000\u0107\u0108\u0005e\u0000\u0000\u0108&\u0001"+
		"\u0000\u0000\u0000\u0109\u010a\u0005b\u0000\u0000\u010a\u010b\u0005r\u0000"+
		"\u0000\u010b\u010c\u0005e\u0000\u0000\u010c\u010d\u0005a\u0000\u0000\u010d"+
		"\u010e\u0005k\u0000\u0000\u010e(\u0001\u0000\u0000\u0000\u010f\u0110\u0005"+
		"c\u0000\u0000\u0110\u0111\u0005o\u0000\u0000\u0111\u0112\u0005n\u0000"+
		"\u0000\u0112\u0113\u0005t\u0000\u0000\u0113\u0114\u0005i\u0000\u0000\u0114"+
		"\u0115\u0005n\u0000\u0000\u0115\u0116\u0005u\u0000\u0000\u0116\u0117\u0005"+
		"e\u0000\u0000\u0117*\u0001\u0000\u0000\u0000\u0118\u0119\u0005r\u0000"+
		"\u0000\u0119\u011a\u0005e\u0000\u0000\u011a\u011b\u0005t\u0000\u0000\u011b"+
		"\u011c\u0005u\u0000\u0000\u011c\u011d\u0005r\u0000\u0000\u011d\u011e\u0005"+
		"n\u0000\u0000\u011e,\u0001\u0000\u0000\u0000\u011f\u0120\u0005(\u0000"+
		"\u0000\u0120.\u0001\u0000\u0000\u0000\u0121\u0122\u0005)\u0000\u0000\u0122"+
		"0\u0001\u0000\u0000\u0000\u0123\u0124\u0005[\u0000\u0000\u01242\u0001"+
		"\u0000\u0000\u0000\u0125\u0126\u0005]\u0000\u0000\u01264\u0001\u0000\u0000"+
		"\u0000\u0127\u0128\u0005{\u0000\u0000\u01286\u0001\u0000\u0000\u0000\u0129"+
		"\u012a\u0005}\u0000\u0000\u012a8\u0001\u0000\u0000\u0000\u012b\u012c\u0005"+
		"<\u0000\u0000\u012c:\u0001\u0000\u0000\u0000\u012d\u012e\u0005<\u0000"+
		"\u0000\u012e\u012f\u0005=\u0000\u0000\u012f<\u0001\u0000\u0000\u0000\u0130"+
		"\u0131\u0005>\u0000\u0000\u0131>\u0001\u0000\u0000\u0000\u0132\u0133\u0005"+
		">\u0000\u0000\u0133\u0134\u0005=\u0000\u0000\u0134@\u0001\u0000\u0000"+
		"\u0000\u0135\u0136\u0005<\u0000\u0000\u0136\u0137\u0005<\u0000\u0000\u0137"+
		"B\u0001\u0000\u0000\u0000\u0138\u0139\u0005>\u0000\u0000\u0139\u013a\u0005"+
		">\u0000\u0000\u013aD\u0001\u0000\u0000\u0000\u013b\u013c\u0005+\u0000"+
		"\u0000\u013cF\u0001\u0000\u0000\u0000\u013d\u013e\u0005-\u0000\u0000\u013e"+
		"H\u0001\u0000\u0000\u0000\u013f\u0140\u0005*\u0000\u0000\u0140J\u0001"+
		"\u0000\u0000\u0000\u0141\u0142\u0005/\u0000\u0000\u0142L\u0001\u0000\u0000"+
		"\u0000\u0143\u0144\u0005%\u0000\u0000\u0144N\u0001\u0000\u0000\u0000\u0145"+
		"\u0146\u0005+\u0000\u0000\u0146\u0147\u0005+\u0000\u0000\u0147P\u0001"+
		"\u0000\u0000\u0000\u0148\u0149\u0005-\u0000\u0000\u0149\u014a\u0005-\u0000"+
		"\u0000\u014aR\u0001\u0000\u0000\u0000\u014b\u014c\u0005&\u0000\u0000\u014c"+
		"T\u0001\u0000\u0000\u0000\u014d\u014e\u0005|\u0000\u0000\u014eV\u0001"+
		"\u0000\u0000\u0000\u014f\u0150\u0005&\u0000\u0000\u0150\u0151\u0005&\u0000"+
		"\u0000\u0151X\u0001\u0000\u0000\u0000\u0152\u0153\u0005|\u0000\u0000\u0153"+
		"\u0154\u0005|\u0000\u0000\u0154Z\u0001\u0000\u0000\u0000\u0155\u0156\u0005"+
		"^\u0000\u0000\u0156\\\u0001\u0000\u0000\u0000\u0157\u0158\u0005!\u0000"+
		"\u0000\u0158^\u0001\u0000\u0000\u0000\u0159\u015a\u0005~\u0000\u0000\u015a"+
		"`\u0001\u0000\u0000\u0000\u015b\u015c\u0005?\u0000\u0000\u015cb\u0001"+
		"\u0000\u0000\u0000\u015d\u015e\u0005:\u0000\u0000\u015ed\u0001\u0000\u0000"+
		"\u0000\u015f\u0160\u0005;\u0000\u0000\u0160f\u0001\u0000\u0000\u0000\u0161"+
		"\u0162\u0005,\u0000\u0000\u0162h\u0001\u0000\u0000\u0000\u0163\u0164\u0005"+
		"\"\u0000\u0000\u0164j\u0001\u0000\u0000\u0000\u0165\u0166\u0005.\u0000"+
		"\u0000\u0166l\u0001\u0000\u0000\u0000\u0167\u0168\u0005=\u0000\u0000\u0168"+
		"n\u0001\u0000\u0000\u0000\u0169\u016a\u0005=\u0000\u0000\u016a\u016b\u0005"+
		"=\u0000\u0000\u016bp\u0001\u0000\u0000\u0000\u016c\u016d\u0005!\u0000"+
		"\u0000\u016d\u016e\u0005=\u0000\u0000\u016er\u0001\u0000\u0000\u0000\u016f"+
		"\u0173\u0007\u0000\u0000\u0000\u0170\u0172\u0007\u0001\u0000\u0000\u0171"+
		"\u0170\u0001\u0000\u0000\u0000\u0172\u0175\u0001\u0000\u0000\u0000\u0173"+
		"\u0171\u0001\u0000\u0000\u0000\u0173\u0174\u0001\u0000\u0000\u0000\u0174"+
		"t\u0001\u0000\u0000\u0000\u0175\u0173\u0001\u0000\u0000\u0000\u0176\u017a"+
		"\u0007\u0002\u0000\u0000\u0177\u0179\u0007\u0003\u0000\u0000\u0178\u0177"+
		"\u0001\u0000\u0000\u0000\u0179\u017c\u0001\u0000\u0000\u0000\u017a\u0178"+
		"\u0001\u0000\u0000\u0000\u017a\u017b\u0001\u0000\u0000\u0000\u017b\u017f"+
		"\u0001\u0000\u0000\u0000\u017c\u017a\u0001\u0000\u0000\u0000\u017d\u017f"+
		"\u00050\u0000\u0000\u017e\u0176\u0001\u0000\u0000\u0000\u017e\u017d\u0001"+
		"\u0000\u0000\u0000\u017fv\u0001\u0000\u0000\u0000\u0180\u0181\u0007\u0004"+
		"\u0000\u0000\u0181x\u0001\u0000\u0000\u0000\u0182\u0183\u0007\u0005\u0000"+
		"\u0000\u0183z\u0001\u0000\u0000\u0000\u0184\u0185\u0005\\\u0000\u0000"+
		"\u0185\u018b\u0005n\u0000\u0000\u0186\u0187\u0005\\\u0000\u0000\u0187"+
		"\u018b\u0005\"\u0000\u0000\u0188\u0189\u0005\\\u0000\u0000\u0189\u018b"+
		"\u0005\\\u0000\u0000\u018a\u0184\u0001\u0000\u0000\u0000\u018a\u0186\u0001"+
		"\u0000\u0000\u0000\u018a\u0188\u0001\u0000\u0000\u0000\u018b|\u0001\u0000"+
		"\u0000\u0000\u018c\u018d\u0005{\u0000\u0000\u018d\u018e\u0005}\u0000\u0000"+
		"\u018e~\u0001\u0000\u0000\u0000\u018f\u0191\u0007\u0006\u0000\u0000\u0190"+
		"\u018f\u0001\u0000\u0000\u0000\u0191\u0192\u0001\u0000\u0000\u0000\u0192"+
		"\u0190\u0001\u0000\u0000\u0000\u0192\u0193\u0001\u0000\u0000\u0000\u0193"+
		"\u0194\u0001\u0000\u0000\u0000\u0194\u0195\u0006?\u0000\u0000\u0195\u0080"+
		"\u0001\u0000\u0000\u0000\u0196\u0197\u0005/\u0000\u0000\u0197\u0198\u0005"+
		"*\u0000\u0000\u0198\u019c\u0001\u0000\u0000\u0000\u0199\u019b\t\u0000"+
		"\u0000\u0000\u019a\u0199\u0001\u0000\u0000\u0000\u019b\u019e\u0001\u0000"+
		"\u0000\u0000\u019c\u019d\u0001\u0000\u0000\u0000\u019c\u019a\u0001\u0000"+
		"\u0000\u0000\u019d\u019f\u0001\u0000\u0000\u0000\u019e\u019c\u0001\u0000"+
		"\u0000\u0000\u019f\u01a0\u0005*\u0000\u0000\u01a0\u01a1\u0005/\u0000\u0000"+
		"\u01a1\u01a2\u0001\u0000\u0000\u0000\u01a2\u01a3\u0006@\u0000\u0000\u01a3"+
		"\u0082\u0001\u0000\u0000\u0000\u01a4\u01a5\u0005/\u0000\u0000\u01a5\u01a6"+
		"\u0005/\u0000\u0000\u01a6\u01aa\u0001\u0000\u0000\u0000\u01a7\u01a9\b"+
		"\u0007\u0000\u0000\u01a8\u01a7\u0001\u0000\u0000\u0000\u01a9\u01ac\u0001"+
		"\u0000\u0000\u0000\u01aa\u01a8\u0001\u0000\u0000\u0000\u01aa\u01ab\u0001"+
		"\u0000\u0000\u0000\u01ab\u01ad\u0001\u0000\u0000\u0000\u01ac\u01aa\u0001"+
		"\u0000\u0000\u0000\u01ad\u01ae\u0006A\u0000\u0000\u01ae\u0084\u0001\u0000"+
		"\u0000\u0000\u0012\u0000\u008c\u008e\u009a\u009c\u00a6\u00a8\u00b2\u00b4"+
		"\u00bc\u00be\u0173\u017a\u017e\u018a\u0192\u019c\u01aa\u0001\u0006\u0000"+
		"\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}