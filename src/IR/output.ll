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
@p = global i32 0
@q = global i32 0
@r = global i32 0
@x = global i32 0
@y = global i32 0
@z = global i32 0
@n = global i32 0
@i = global i32 0
@.str.0 = private unnamed_addr constant [4 x i8] c"YES\00"
@.str.1 = private unnamed_addr constant [3 x i8] c"NO\00"
define i32 @main() {
entry:
	%0 = load i32, ptr @n;
	%1 = call i32 @getInt()
	store i32 %1, ptr @n;
	%2 = load i32, ptr @i;
	store i32 0, ptr @i;
	br label %for.cond.2.1;
for.cond.2.1:
	%3 = load i32, ptr @i;
	%4 = load i32, ptr @n;
	%5 = icmp slt i32 %3, %4;
	br i1 %5, label %for.body.2.1,label %for.end.2.1;
for.body.2.1:
	%6 = load i32, ptr @p;
	%7 = call i32 @getInt()
	store i32 %7, ptr @p;
	%8 = load i32, ptr @q;
	%9 = call i32 @getInt()
	store i32 %9, ptr @q;
	%10 = load i32, ptr @r;
	%11 = call i32 @getInt()
	store i32 %11, ptr @r;
	%12 = load i32, ptr @x;
	%13 = load i32, ptr @x;
	%14 = load i32, ptr @p;
	%15 = add i32 %13, %14
	store i32 %15, ptr @x;
	%16 = load i32, ptr @y;
	%17 = load i32, ptr @y;
	%18 = load i32, ptr @q;
	%19 = add i32 %17, %18
	store i32 %19, ptr @y;
	%20 = load i32, ptr @z;
	%21 = load i32, ptr @z;
	%22 = load i32, ptr @r;
	%23 = add i32 %21, %22
	store i32 %23, ptr @z;
	br label %for.step.2.1;
for.step.2.1:
	%24 = load i32, ptr @i;
	%25 = add i32 %24, 1
	store i32 %25, ptr @i;
	br label %for.cond.2.1;
for.end.2.1:
	%26 = load i32, ptr @x;
	%27 = icmp eq i32 %26, 0;
	%28 = icmp ne i1 %27, 0;
	br i1 %28, label %logic.rhs.0,label %logic.end.0;
logic.rhs.0:
	%29 = load i32, ptr @y;
	%30 = icmp eq i32 %29, 0;
	%31 = icmp ne i1 %30, 0;
	br label %logic.end.0;
logic.end.0:
	%32 = phi i1 [ 0, %for.end.2.1 ], [ %31, %logic.rhs.0 ]
	%33 = icmp ne i1 %32, 0;
	br i1 %33, label %logic.rhs.1,label %logic.end.1;
logic.rhs.1:
	%34 = load i32, ptr @z;
	%35 = icmp eq i32 %34, 0;
	%36 = icmp ne i1 %35, 0;
	br label %logic.end.1;
logic.end.1:
	%37 = phi i1 [ 0, %logic.end.0 ], [ %36, %logic.rhs.1 ]
	br i1 %37, label %if.then.2.2,label %if.else.2.2;
if.then.2.2:
	call void @print(ptr @.str.0)
	br label %if.end.2.2;
if.else.2.2:
	call void @print(ptr @.str.1)
	br label %if.end.2.2;
if.end.2.2:
	ret i32 0;
}

