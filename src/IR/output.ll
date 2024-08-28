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
%class.Queue_int = type {ptr, i32, i32 }
@.str.0 = private unnamed_addr constant [37 x i8] c"Warning: Queue_int::pop: empty queue\00"
@.str.1 = private unnamed_addr constant [27 x i8] c"q.size() != N after pushes\00"
@.str.2 = private unnamed_addr constant [10 x i8] c"Head != i\00"
@.str.3 = private unnamed_addr constant [21 x i8] c"Failed: q.pop() != i\00"
@.str.4 = private unnamed_addr constant [22 x i8] c"q.size() != N - i - 1\00"
@.str.5 = private unnamed_addr constant [14 x i8] c"Passed tests.\00"
define void @Queue_int.push(ptr %this, i32 %v) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%v.2.1 = alloca i32;
	store i32 %v, ptr %v.2.1;
	%0 = call i32 @Queue_int.size(ptr %this.this)
	%1 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%2 = load ptr, ptr %1;
	%3 = call i32 @array.size(ptr %2)
	%4 = sub i32 %3, 1
	%5 = icmp eq i32 %0, %4;
	br i1 %5, label %if.then.0,label %if.else.0;
if.then.0:
	call void @Queue_int.doubleStorage(ptr %this.this)
	br label %if.end.0;
if.else.0:
	br label %if.end.0;
if.end.0:
	%6 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%7 = load ptr, ptr %6;
	%8 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 2;
	%9 = load i32, ptr %8;
	%10 = getelementptr ptr, ptr %7, i32 %9;
	%11 = load i32, ptr %10;
	%12 = load i32, ptr %v.2.1;
	store i32 %12, ptr %10;
	%13 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 2;
	%14 = load i32, ptr %13;
	%15 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 2;
	%16 = load i32, ptr %15;
	%17 = add i32 %16, 1
	%18 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%19 = load ptr, ptr %18;
	%20 = call i32 @array.size(ptr %19)
	%21 = srem i32 %17, %20
	store i32 %21, ptr %13;
	ret void;
}

define i32 @Queue_int.top(ptr %this) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%0 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%1 = load ptr, ptr %0;
	%2 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 1;
	%3 = load i32, ptr %2;
	%4 = getelementptr ptr, ptr %1, i32 %3;
	%5 = load i32, ptr %4;
	ret i32 %5;
}

define i32 @Queue_int.pop(ptr %this) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%0 = call i32 @Queue_int.size(ptr %this.this)
	%1 = icmp eq i32 %0, 0;
	%res.2.3 = alloca i32;
	br i1 %1, label %if.then.0,label %if.else.0;
if.then.0:
	call void @println(ptr @.str.0)
	br label %if.end.0;
if.else.0:
	br label %if.end.0;
if.end.0:
	%2 = call i32 @Queue_int.top(ptr %this.this)
	store i32 %2, ptr %res.2.3;
	%3 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 1;
	%4 = load i32, ptr %3;
	%5 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 1;
	%6 = load i32, ptr %5;
	%7 = add i32 %6, 1
	%8 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%9 = load ptr, ptr %8;
	%10 = call i32 @array.size(ptr %9)
	%11 = srem i32 %7, %10
	store i32 %11, ptr %3;
	%12 = load i32, ptr %res.2.3;
	ret i32 %12;
}

define i32 @Queue_int.size(ptr %this) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%0 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 2;
	%1 = load i32, ptr %0;
	%2 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%3 = load ptr, ptr %2;
	%4 = call i32 @array.size(ptr %3)
	%5 = add i32 %1, %4
	%6 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 1;
	%7 = load i32, ptr %6;
	%8 = sub i32 %5, %7
	%9 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%10 = load ptr, ptr %9;
	%11 = call i32 @array.size(ptr %10)
	%12 = srem i32 %8, %11
	ret i32 %12;
}

define void @Queue_int.doubleStorage(ptr %this) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%copy.2.5 = alloca ptr;
	%0 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%1 = load ptr, ptr %0;
	store ptr %1, ptr %copy.2.5;
	%begCopy.2.5 = alloca i32;
	%2 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 1;
	%3 = load i32, ptr %2;
	store i32 %3, ptr %begCopy.2.5;
	%endCopy.2.5 = alloca i32;
	%4 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 2;
	%5 = load i32, ptr %4;
	store i32 %5, ptr %endCopy.2.5;
	%6 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%7 = load ptr, ptr %6;
	%8 = load ptr, ptr %copy.2.5;
	%9 = call i32 @array.size(ptr %8)
	%10 = mul i32 %9, 2
	%11 = call ptr @_malloc_array(i32 %10)
	store ptr %11, ptr %6;
	%12 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 1;
	%13 = load i32, ptr %12;
	store i32 0, ptr %12;
	%14 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 2;
	%15 = load i32, ptr %14;
	store i32 0, ptr %14;
	%i.2.5 = alloca i32;
	%16 = load i32, ptr %begCopy.2.5;
	store i32 %16, ptr %i.2.5;
	br label %while.cond.0;
while.cond.0:
	%17 = load i32, ptr %i.2.5;
	%18 = load i32, ptr %endCopy.2.5;
	%19 = icmp ne i32 %17, %18;
	br i1 %19, label %while.body.0,label %while.end.0;
while.body.0:
	%20 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%21 = load ptr, ptr %20;
	%22 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 2;
	%23 = load i32, ptr %22;
	%24 = getelementptr ptr, ptr %21, i32 %23;
	%25 = load i32, ptr %24;
	%26 = load ptr, ptr %copy.2.5;
	%27 = load i32, ptr %i.2.5;
	%28 = getelementptr ptr, ptr %26, i32 %27;
	%29 = load i32, ptr %28;
	store i32 %29, ptr %24;
	%30 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 2;
	%31 = load i32, ptr %30;
	%32 = add i32 %31, 1
	store i32 %32, ptr %30;
	%33 = load i32, ptr %i.2.5;
	%34 = load i32, ptr %i.2.5;
	%35 = add i32 %34, 1
	%36 = load ptr, ptr %copy.2.5;
	%37 = call i32 @array.size(ptr %36)
	%38 = srem i32 %35, %37
	store i32 %38, ptr %i.2.5;
	br label %while.cond.0;
while.end.0:
	ret void;
}

define void @Queue_int.Queue_int(ptr %this) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%0 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 1;
	%1 = load i32, ptr %0;
	store i32 0, ptr %0;
	%2 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 2;
	%3 = load i32, ptr %2;
	store i32 0, ptr %2;
	%4 = getelementptr %class.Queue_int, ptr %this.this, i32 0, i32 0;
	%5 = load ptr, ptr %4;
	%6 = call ptr @_malloc_array(i32 16)
	store ptr %6, ptr %4;
	ret void;
}

define i32 @main() {
entry:
	%q.1.2 = alloca ptr;
	%0 = call ptr @_malloc(i32 3)
	call void @Queue_int.Queue_int(ptr %0)
	store ptr %0, ptr %q.1.2;
	%i.1.2 = alloca i32;
	%N.1.2 = alloca i32;
	store i32 100, ptr %N.1.2;
	%1 = load i32, ptr %i.1.2;
	store i32 0, ptr %i.1.2;
	%head.3.1 = alloca i32;
	br label %for.cond.0;
for.cond.0:
	%2 = load i32, ptr %i.1.2;
	%3 = load i32, ptr %N.1.2;
	%4 = icmp slt i32 %2, %3;
	br i1 %4, label %for.body.0,label %for.end.0;
for.body.0:
	%5 = load ptr, ptr %q.1.2;
	%6 = load i32, ptr %i.1.2;
	call void @Queue_int.push(ptr %5, i32 %6)
	br label %for.step.0;
for.step.0:
	%7 = load i32, ptr %i.1.2;
	%8 = add i32 %7, 1
	store i32 %8, ptr %i.1.2;
	br label %for.cond.0;
for.end.0:
	%9 = load ptr, ptr %q.1.2;
	%10 = call i32 @Queue_int.size(ptr %9)
	%11 = load i32, ptr %N.1.2;
	%12 = icmp ne i32 %10, %11;
	br i1 %12, label %if.then.1,label %if.else.1;
if.then.1:
	call void @println(ptr @.str.1)
	ret i32 1;
if.else.1:
	br label %if.end.1;
if.end.1:
	%13 = load i32, ptr %i.1.2;
	store i32 0, ptr %i.1.2;
	br label %for.cond.2;
for.cond.2:
	%14 = load i32, ptr %i.1.2;
	%15 = load i32, ptr %N.1.2;
	%16 = icmp slt i32 %14, %15;
	br i1 %16, label %for.body.2,label %for.end.2;
for.body.2:
	%17 = load ptr, ptr %q.1.2;
	%18 = call i32 @Queue_int.top(ptr %17)
	store i32 %18, ptr %head.3.1;
	%19 = load i32, ptr %head.3.1;
	%20 = load i32, ptr %i.1.2;
	%21 = icmp ne i32 %19, %20;
	br i1 %21, label %if.then.3,label %if.else.3;
if.then.3:
	call void @println(ptr @.str.2)
	ret i32 1;
if.else.3:
	br label %if.end.3;
if.end.3:
	%22 = load ptr, ptr %q.1.2;
	%23 = call i32 @Queue_int.pop(ptr %22)
	%24 = load i32, ptr %i.1.2;
	%25 = icmp ne i32 %23, %24;
	br i1 %25, label %if.then.4,label %if.else.4;
if.then.4:
	call void @println(ptr @.str.3)
	ret i32 1;
if.else.4:
	br label %if.end.4;
if.end.4:
	%26 = load ptr, ptr %q.1.2;
	%27 = call i32 @Queue_int.size(ptr %26)
	%28 = load i32, ptr %N.1.2;
	%29 = load i32, ptr %i.1.2;
	%30 = sub i32 %28, %29
	%31 = sub i32 %30, 1
	%32 = icmp ne i32 %27, %31;
	br i1 %32, label %if.then.5,label %if.else.5;
if.then.5:
	call void @println(ptr @.str.4)
	ret i32 1;
if.else.5:
	br label %if.end.5;
if.end.5:
	br label %for.step.2;
for.step.2:
	%33 = load i32, ptr %i.1.2;
	%34 = add i32 %33, 1
	store i32 %34, ptr %i.1.2;
	br label %for.cond.2;
for.end.2:
	call void @println(ptr @.str.5)
	ret i32 0;
}

