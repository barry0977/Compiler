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
@n = global i32 0
@p = global i32 0
@k = global i32 0
@i = global i32 0
@.str.0 = private unnamed_addr constant [4 x i8] c"<< \00"
@.str.1 = private unnamed_addr constant [2 x i8] c"(\00"
@.str.2 = private unnamed_addr constant [3 x i8] c") \00"
@.str.3 = private unnamed_addr constant [2 x i8] c" \00"
@.str.4 = private unnamed_addr constant [4 x i8] c">> \00"
define i32 @main() {
entry:
	%0 = load i32, ptr @n;
	%1 = call i32 @getInt()
	store i32 %1, ptr @n;
	%2 = load i32, ptr @p;
	%3 = call i32 @getInt()
	store i32 %3, ptr @p;
	%4 = load i32, ptr @k;
	%5 = call i32 @getInt()
	store i32 %5, ptr @k;
	%6 = load i32, ptr @p;
	%7 = load i32, ptr @k;
	%8 = sub i32 %6, %7
	%9 = icmp sgt i32 %8, 1;
	br i1 %9, label %if.then.0,label %if.else.0;
if.then.0:
	call void @print(ptr @.str.0)
	br label %if.end.0;
if.else.0:
	br label %if.end.0;
if.end.0:
	%10 = load i32, ptr @i;
	%11 = load i32, ptr @p;
	%12 = load i32, ptr @k;
	%13 = sub i32 %11, %12
	store i32 %13, ptr @i;
	br label %for.cond.1;
for.cond.1:
	%14 = load i32, ptr @i;
	%15 = load i32, ptr @p;
	%16 = load i32, ptr @k;
	%17 = add i32 %15, %16
	%18 = icmp sle i32 %14, %17;
	br i1 %18, label %for.body.1,label %for.end.1;
for.body.1:
	%19 = load i32, ptr @i;
	%20 = icmp sle i32 1, %19;
	%21 = icmp ne i1 %20, 0;
	br i1 %21, label %logic.rhs.2,label %logic.end.2;
logic.rhs.2:
	%22 = load i32, ptr @i;
	%23 = load i32, ptr @n;
	%24 = icmp sle i32 %22, %23;
	%25 = icmp ne i1 %24, 0;
	br label %logic.end.2;
logic.end.2:
	%26 = select i1 %21, i1 %25, i1 0
	br i1 %26, label %if.then.3,label %if.else.3;
if.then.3:
	%27 = load i32, ptr @i;
	%28 = load i32, ptr @p;
	%29 = icmp eq i32 %27, %28;
	br i1 %29, label %if.then.4,label %if.else.4;
if.then.4:
	call void @print(ptr @.str.1)
	%30 = load i32, ptr @i;
	%31 = call ptr @toString(i32 %30)
	call void @print(ptr %31)
	call void @print(ptr @.str.2)
	br label %if.end.4;
if.else.4:
	%32 = load i32, ptr @i;
	call void @printInt(i32 %32)
	call void @print(ptr @.str.3)
	br label %if.end.4;
if.end.4:
	br label %if.end.3;
if.else.3:
	br label %if.end.3;
if.end.3:
	br label %for.step.1;
for.step.1:
	%33 = load i32, ptr @i;
	%34 = add i32 %33, 1
	store i32 %34, ptr @i;
	br label %for.cond.1;
for.end.1:
	%35 = load i32, ptr @p;
	%36 = load i32, ptr @k;
	%37 = add i32 %35, %36
	%38 = load i32, ptr @n;
	%39 = icmp slt i32 %37, %38;
	br i1 %39, label %if.then.5,label %if.else.5;
if.then.5:
	call void @print(ptr @.str.4)
	br label %if.end.5;
if.else.5:
	br label %if.end.5;
if.end.5:
	ret i32 0;
}

