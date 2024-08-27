declare void @print(ptr, ptr, i32, i32)
declare void @println()
declare void @printInt()
declare void @printlnInt()
declare ptr @getString()
declare i32 @getInt()
declare ptr @toString(i32)
declare i32 @string.length(ptr)
declare ptr @string.substring(ptr, i32, i32)
declare i32 @string.parseInt(ptr)
declare i32 @string.ord(ptr, i32)
declare ptr @string.add(ptr, ptr)
declare i1 @string.equal(ptr, ptr)
declare i1 @string.notEqual(ptr, ptr)
declare i1 @string.less(ptr, ptr)
declare i1 @string.lessOrEqual(ptr, ptr)
declare i1 @string.greater(ptr, ptr)
declare i1 @string.greaterOrEqual(ptr, ptr)
declare i32 @array.size(ptr)
declare ptr @_malloc(i32)
declare ptr @_malloc_array(i32)
@.str.0 = private unnamed_addr constant [4 x i8] c"aaa\00"
@.str.1 = private unnamed_addr constant [4 x i8] c"bbb\00"
@.str.2 = private unnamed_addr constant [4 x i8] c"ccc\00"
@.str.3 = private unnamed_addr constant [4 x i8] c"ddd\00"
@.str.4 = private unnamed_addr constant [4 x i8] c"eee\00"
define void @foo1() {
entry:
	call void @println(ptr @.str.0)
	ret i32 0;
}

define void @foo2() {
entry:
	call void @println(ptr @.str.1)
	ret i32 0;
}

define void @foo3() {
entry:
	call void @println(ptr @.str.2)
	ret i32 0;
}

define void @foo4() {
entry:
	call void @println(ptr @.str.3)
	ret i32 0;
}

define void @foo5() {
entry:
	call void @println(ptr @.str.4)
	ret i32 0;
}

define i32 @main() {
entry:
	%a.1.6 = alloca i32;
	%0 = call i32 @getInt()
	store i32 %0, ptr %a.1.6;
	%b.1.6 = alloca i32;
	%1 = call i32 @getInt()
	store i32 %1, ptr %b.1.6;
	%c.1.6 = alloca i32;
	%2 = call i32 @getInt()
	store i32 %2, ptr %c.1.6;
	%3 = load i32, ptr %a.1.6;
	%4 = add i32 %3, 1
	store i32 %4, ptr %a.1.6;
	%5 = load i32, ptr %b.1.6;
	%6 = icmp eq i32 %3, %5;
	br i1 %6, label %cond.true.0,label %cond.false.0;
cond.true.0:
	%7 = load i32, ptr %a.1.6;
	%8 = add i32 %7, 1
	store i32 %8, ptr %a.1.6;
	%9 = load i32, ptr %b.1.6;
	%10 = icmp eq i32 %7, %9;
	br i1 %10, label %cond.true.1,label %cond.false.1;
cond.true.1:
	call void @foo1()
	br label %cond.end.1;
cond.false.1:
	call void @foo2()
	br label %cond.end.1;
cond.end.1:
	br label %cond.end.0;
cond.false.0:
	%11 = load i32, ptr %a.1.6;
	%12 = add i32 %11, 1
	store i32 %12, ptr %a.1.6;
	%13 = load i32, ptr %b.1.6;
	%14 = icmp eq i32 %11, %13;
	br i1 %14, label %cond.true.2,label %cond.false.2;
cond.true.2:
	%15 = load i32, ptr %b.1.6;
	%16 = add i32 %15, 1
	store i32 %16, ptr %b.1.6;
	%17 = load i32, ptr %c.1.6;
	%18 = add i32 %17, 1
	store i32 %18, ptr %c.1.6;
	%19 = icmp eq i32 %16, %17;
	br i1 %19, label %cond.true.3,label %cond.false.3;
cond.true.3:
	call void @foo3()
	br label %cond.end.3;
cond.false.3:
	call void @foo4()
	br label %cond.end.3;
cond.end.3:
	br label %cond.end.2;
cond.false.2:
	call void @foo5()
	br label %cond.end.2;
cond.end.2:
	br label %cond.end.0;
cond.end.0:
	%20 = load i32, ptr %a.1.6;
	call void @printlnInt(i32 %20)
	%21 = load i32, ptr %b.1.6;
	call void @printlnInt(i32 %21)
	%22 = load i32, ptr %c.1.6;
	call void @printlnInt(i32 %22)
	ret i32 0;
}

