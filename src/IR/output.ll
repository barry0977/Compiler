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
%class.A = type {i32 }
@.str.0 = private unnamed_addr constant [18 x i8] c"thisisastring//\\\0A\00"
define void @A.A(ptr %this) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	ret void;
}

define i32 @B() {
entry:
	%c.1.2 = alloca i32;
	%c.2.1 = alloca i32;
	%s.2.1 = alloca i32;
	%0 = load i32, ptr %s.2.1;
	%1 = add i32 %0, 1
	%2 = load i32, ptr %c.1.2;
	ret i32 %2;
}

define i32 @main() {
entry:
	%k.1.3 = alloca i32;
	%u.1.3 = alloca ptr;
	%0 = call ptr @_malloc_array(i32 3)
	store ptr %0, ptr %u.1.3;
	%1 = load ptr, ptr %u.1.3;
	%2 = getelementptr ptr, ptr %1, i32 1;
	%3 = load ptr, ptr %2;
	%4 = call ptr @_malloc_array(i32 2)
	store ptr %4, ptr %2;
	%5 = load ptr, ptr %u.1.3;
	%6 = getelementptr ptr, ptr %5, i32 3;
	%7 = load ptr, ptr %6;
	%8 = call ptr @_malloc_array(i32 999)
	store ptr %8, ptr %6;
	%9 = load ptr, ptr %u.1.3;
	%10 = getelementptr ptr, ptr %9, i32 2;
	%11 = load ptr, ptr %10;
	store ptr null, ptr %10;
	%12 = call ptr @_malloc_array(i32 2)
	%13 = getelementptr ptr, ptr %12, i32 3;
	%14 = load ptr, ptr %13;
	%15 = load ptr, ptr %u.1.3;
	store ptr null, ptr %u.1.3;
	%s.1.3 = alloca ptr;
	%16 = call i32 @getInt()
	%17 = call ptr @toString(i32 %16)
	store ptr %17, ptr %s.1.3;
	%18 = load i32, ptr %k.1.3;
	store i32 1, ptr %k.1.3;
	%aa.1.3 = alloca i32;
	br label %for.cond.0;
for.cond.0:
	%19 = load i32, ptr %k.1.3;
	%20 = load ptr, ptr %s.1.3;
	%21 = call ptr @string.add(ptr %20, ptr @.str.0)
	%22 = call ptr @string.substring(ptr %21, i32 1, i32 3)
	%23 = call i32 @string.ord(ptr %22, i32 0)
	%24 = icmp slt i32 %19, %23;
	br i1 %24, label %for.body.0,label %for.end.0;
for.body.0:
	%25 = call ptr @getString()
	call void @println(ptr %25)
	br label %for.step.0;
for.step.0:
	%26 = load i32, ptr %k.1.3;
	%27 = load i32, ptr %k.1.3;
	%28 = load ptr, ptr %u.1.3;
	%29 = call i32 @array.size(ptr %28)
	%30 = add i32 %27, %29
	store i32 %30, ptr %k.1.3;
	br label %for.cond.0;
for.end.0:
	br label %for.cond.1;
for.cond.1:
	br label %for.body.1;
for.body.1:
	br label %while.cond.2;
while.cond.2:
	br i1 1, label %while.body.2,label %while.end.2;
while.body.2:
	br label %while.cond.2;
while.end.2:
	br label %for.step.1;
for.step.1:
	br label %for.cond.1;
for.end.1:
	%31 = add i32 1, 1
	store i32 %31, ptr %aa.1.3;
	%32 = load i32, ptr %aa.1.3;
	%33 = add i32 %32, 1
	store i32 %33, ptr %aa.1.3;
	%34 = load i32, ptr %aa.1.3;
	%35 = add i32 %34, 1
	store i32 %35, ptr %aa.1.3;
	ret i32 0;
}

