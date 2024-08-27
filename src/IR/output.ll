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
@t = global i32 0
@l = global i32 0
@i = global i32 0
@s = global ptr null
define i32 @main() {
entry:
	%0 = load i32, ptr @t;
	%1 = call i32 @getInt()
	store i32 %1, ptr @t;
	%2 = load i32, ptr @i;
	store i32 0, ptr @i;
	br label %for.cond.2.1;
for.cond.2.1:
	%3 = load i32, ptr @i;
	%4 = load i32, ptr @t;
	%5 = icmp slt i32 %3, %4;
	br i1 %5, label %for.body.2.1,label %for.end.2.1;
for.body.2.1:
	%6 = load ptr, ptr @s;
	%7 = call ptr @getString()
	store ptr %7, ptr @s;
	%8 = load i32, ptr @l;
	%9 = load ptr, ptr @s;
	%10 = call i32 @string.length(ptr @s)
	store i32 %10, ptr @l;
	%11 = load i32, ptr @l;
	%12 = icmp sgt i32 %11, 10;
	br i1 %12, label %if.then.3.1,label %if.else.3.1;
if.then.3.1:
	%13 = load ptr, ptr @s;
	%14 = call ptr @string.substring(ptr @s, i32 0, i32 1)
	%15 = load i32, ptr @l;
	%16 = sub i32 %15, 2
	%17 = call ptr @toString(i32 %16)
	%18 = call ptr @string.add(ptr @s, ptr @l)
	%19 = load ptr, ptr @s;
	%20 = load i32, ptr @l;
	%21 = sub i32 %20, 1
	%22 = load i32, ptr @l;
	%23 = call ptr @string.substring(ptr @s, i32 %21, i32 %22)
	%24 = call ptr @string.add(ptr @l, ptr @l)
	call void @println(ptr %24)
	br label %if.end.3.1;
if.else.3.1:
	%25 = load ptr, ptr @s;
	call void @println(ptr %25)
	br label %if.end.3.1;
if.end.3.1:
	br label %for.step.2.1;
for.step.2.1:
	%26 = load i32, ptr @i;
	%27 = add i32 %26, 1
	store i32 %27, ptr @i;
	br label %for.cond.2.1;
for.end.2.1:
	ret i32 0;
}

