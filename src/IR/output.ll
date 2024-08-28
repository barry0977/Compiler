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
@a = global ptr null
@str = global ptr null
@.str.0 = private unnamed_addr constant [5 x i8] c"str1\00"
@.str.1 = private unnamed_addr constant [5 x i8] c"str2\00"
@.str.2 = private unnamed_addr constant [5 x i8] c"str3\00"
@.str.3 = private unnamed_addr constant [5 x i8] c"str4\00"
@.str.4 = private unnamed_addr constant [5 x i8] c"str5\00"
@.str.5 = private unnamed_addr constant [5 x i8] c"str6\00"
@.str.6 = private unnamed_addr constant [5 x i8] c"str7\00"
@.str.7 = private unnamed_addr constant [5 x i8] c"str8\00"
@.str.8 = private unnamed_addr constant [5 x i8] c"str9\00"
@.str.9 = private unnamed_addr constant [6 x i8] c"str10\00"
define void @_init_() {
entry:
	%0 = call ptr @_malloc_array(i32 30)
	%1 = alloca i32;
	store i32 0, ptr %1;
	br label %for.cond._1;
for.cond._1:
	%2 = load i32, ptr %1;
	%3 = icmp slt i32 %2, 30;
	br i1 %3, label %for.body._1,label %for.end._1;
for.body._1:
	%4 = getelementptr ptr, ptr %0, i32 %2;
	%5 = call ptr @_malloc_array(i32 30)
	store ptr %5, ptr %4;
	br label %for.step._1;
for.step._1:
	%6 = add i32 %2, 1
	store i32 %6, ptr %1;
	br label %for.cond._1;
for.end._1:
	store ptr %0, ptr @a;
	%7 = call ptr @_malloc_array(i32 30)
	store ptr %7, ptr @str;
	ret void
}

define i32 @main() {
entry:
	call void @_init_()
	%i.1.1 = alloca i32;
	%j.1.1 = alloca i32;
	%0 = load i32, ptr %i.1.1;
	store i32 0, ptr %i.1.1;
	%sum.3.1 = alloca i32;
	br label %for.cond.0;
for.cond.0:
	%1 = load i32, ptr %i.1.1;
	%2 = icmp sle i32 %1, 29;
	br i1 %2, label %for.body.0,label %for.end.0;
for.body.0:
	store i32 0, ptr %sum.3.1;
	%3 = load ptr, ptr @str;
	%4 = load i32, ptr %i.1.1;
	%5 = getelementptr ptr, ptr %3, i32 %4;
	%6 = load ptr, ptr %5;
	%7 = load ptr, ptr @a;
	%8 = load i32, ptr %i.1.1;
	%9 = getelementptr ptr, ptr %7, i32 %8;
	%10 = load ptr, ptr %9;
	%11 = getelementptr ptr, ptr %10, i32 0;
	%12 = load i32, ptr %11;
	%13 = call ptr @toString(i32 %12)
	store ptr %13, ptr %5;
	%14 = load i32, ptr %j.1.1;
	store i32 0, ptr %j.1.1;
	br label %for.cond.1;
for.cond.1:
	%15 = load i32, ptr %j.1.1;
	%16 = load i32, ptr %i.1.1;
	%17 = icmp slt i32 %15, %16;
	br i1 %17, label %for.body.1,label %for.end.1;
for.body.1:
	%18 = load i32, ptr %j.1.1;
	%19 = and i32 %18, 1
	%20 = icmp eq i32 %19, 0;
	br i1 %20, label %if.then.2,label %if.else.2;
if.then.2:
	%21 = load i32, ptr %sum.3.1;
	%22 = load i32, ptr %sum.3.1;
	%23 = load ptr, ptr @a;
	%24 = load i32, ptr %i.1.1;
	%25 = getelementptr ptr, ptr %23, i32 %24;
	%26 = load ptr, ptr %25;
	%27 = getelementptr ptr, ptr %26, i32 0;
	%28 = load i32, ptr %27;
	%29 = add i32 %22, %28
	store i32 %29, ptr %sum.3.1;
	br label %if.end.2;
if.else.2:
	br label %if.end.2;
if.end.2:
	%30 = load i32, ptr %j.1.1;
	%31 = and i32 %30, 1
	%32 = icmp eq i32 %31, 1;
	br i1 %32, label %if.then.3,label %if.else.3;
if.then.3:
	%33 = load i32, ptr %sum.3.1;
	%34 = load i32, ptr %sum.3.1;
	%35 = load ptr, ptr @a;
	%36 = load i32, ptr %i.1.1;
	%37 = getelementptr ptr, ptr %35, i32 %36;
	%38 = load ptr, ptr %37;
	%39 = getelementptr ptr, ptr %38, i32 29;
	%40 = load i32, ptr %39;
	%41 = add i32 %34, %40
	store i32 %41, ptr %sum.3.1;
	br label %if.end.3;
if.else.3:
	br label %if.end.3;
if.end.3:
	br label %for.step.1;
for.step.1:
	%42 = load i32, ptr %j.1.1;
	%43 = add i32 %42, 1
	store i32 %43, ptr %j.1.1;
	br label %for.cond.1;
for.end.1:
	%44 = call ptr @string.add(ptr @.str.0, ptr @.str.1)
	%45 = call ptr @string.add(ptr %44, ptr @.str.2)
	%46 = call ptr @string.add(ptr %45, ptr @.str.3)
	%47 = call ptr @string.add(ptr %46, ptr @.str.4)
	%48 = call ptr @string.add(ptr %47, ptr @.str.5)
	%49 = call ptr @string.add(ptr %48, ptr @.str.6)
	%50 = call ptr @string.add(ptr %49, ptr @.str.7)
	%51 = call ptr @string.add(ptr %50, ptr @.str.8)
	%52 = call ptr @string.add(ptr %51, ptr @.str.9)
	call void @println(ptr %52)
	br label %for.step.0;
for.step.0:
	%53 = load i32, ptr %i.1.1;
	%54 = add i32 %53, 1
	store i32 %54, ptr %i.1.1;
	br label %for.cond.0;
for.end.0:
	ret i32 0;
}

