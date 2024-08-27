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
@a = global ptr null
@i = global i32 0
define void @_init_() {
entry:
	%0 = call ptr @_malloc_array(i32 20)
	store ptr %0, ptr @a;
ret void
}

define i32 @jud(i32 %x) {
entry:
	%x.1.1 = alloca i32;
	store i32 %x, ptr %x.1.1;
	%i.1.1 = alloca i32;
	%j.1.1 = alloca i32;
	%0 = load i32, ptr %i.1.1;
	store i32 0, ptr %i.1.1;
	%flag.2.1 = alloca i1;
	store i1 0, ptr %flag.2.1;
	br label %for.cond.2.1;
for.cond.2.1:
	%1 = load i32, ptr %i.1.1;
	%2 = load i32, ptr @n;
	%3 = load i32, ptr %x.1.1;
	%4 = sdiv i32 %2, %3
	%5 = icmp slt i32 %1, %4;
	br i1 %5, label %for.body.2.1,label %for.end.2.1;
for.body.2.1:
	%6 = load i32, ptr %j.1.1;
	store i32 0, ptr %j.1.1;
	br label %for.cond.3.1;
for.cond.3.1:
	%7 = load i32, ptr %j.1.1;
	%8 = load i32, ptr %x.1.1;
	%9 = sub i32 %8, 1
	%10 = icmp slt i32 %7, %9;
	br i1 %10, label %for.body.3.1,label %for.end.3.1;
for.body.3.1:
	%11 = load ptr, ptr @a;
	%12 = load i32, ptr %i.1.1;
	%13 = load i32, ptr %x.1.1;
	%14 = mul i32 %12, %13
	%15 = load i32, ptr %j.1.1;
	%16 = add i32 %14, %15
	%17 = getelementptr ptr, ptr %11, i32 %16;
	%18 = load i32, ptr %17;
	%19 = load ptr, ptr @a;
	%20 = load i32, ptr %i.1.1;
	%21 = load i32, ptr %x.1.1;
	%22 = mul i32 %20, %21
	%23 = load i32, ptr %j.1.1;
	%24 = add i32 %22, %23
	%25 = add i32 %24, 1
	%26 = getelementptr ptr, ptr %19, i32 %25;
	%27 = load i32, ptr %26;
	%28 = icmp sgt i32 %18, %27;
	br i1 %28, label %if.then.4.1,label %if.else.4.1;
if.then.4.1:
	%29 = load i1, ptr %flag.2.1;
	store i1 1, ptr %flag.2.1;
	br label %if.end.4.1;
if.else.4.1:
	br label %if.end.4.1;
if.end.4.1:
	br label %for.step.3.1;
for.step.3.1:
	%30 = load i32, ptr %j.1.1;
	%31 = add i32 %30, 1
	store i32 %31, ptr %j.1.1;
	br label %for.cond.3.1;
for.end.3.1:
	%32 = load i1, ptr %flag.2.1;
	%33 = xor i1 %32, 1
	br i1 %33, label %if.then.3.2,label %if.else.3.2;
if.then.3.2:
	ret i32 1;
if.else.3.2:
	br label %if.end.3.2;
if.end.3.2:
	br label %for.step.2.1;
for.step.2.1:
	%34 = load i32, ptr %i.1.1;
	%35 = add i32 %34, 1
	store i32 %35, ptr %i.1.1;
	br label %for.cond.2.1;
for.end.2.1:
	ret i32 0;
}

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
	%6 = load ptr, ptr @a;
	%7 = load i32, ptr @i;
	%8 = getelementptr ptr, ptr %6, i32 %7;
	%9 = load i32, ptr %8;
	%10 = call i32 @getInt()
	store i32 %10, ptr %8;
	br label %for.step.2.1;
for.step.2.1:
	%11 = load i32, ptr @i;
	%12 = add i32 %11, 1
	store i32 %12, ptr @i;
	br label %for.cond.2.1;
for.end.2.1:
	%13 = load i32, ptr @i;
	%14 = load i32, ptr @n;
	store i32 %14, ptr @i;
	br label %for.cond.2.2;
for.cond.2.2:
	%15 = load i32, ptr @i;
	%16 = icmp sgt i32 %15, 0;
	br i1 %16, label %for.body.2.2,label %for.end.2.2;
for.body.2.2:
	%17 = load i32, ptr @i;
	%18 = call i32 @jud(i32 %17)
	%19 = icmp sgt i32 %18, 0;
	br i1 %19, label %if.then.3.1,label %if.else.3.1;
if.then.3.1:
	%20 = load i32, ptr @i;
	%21 = call ptr @toString(i32 %20)
	call void @print(ptr %21)
	ret i32 0;
if.else.3.1:
	br label %if.end.3.1;
if.end.3.1:
	br label %for.step.2.2;
for.step.2.2:
	%22 = load i32, ptr @i;
	%23 = load i32, ptr @i;
	%24 = sdiv i32 %23, 2
	store i32 %24, ptr @i;
	br label %for.cond.2.2;
for.end.2.2:
	ret i32 0;
}

