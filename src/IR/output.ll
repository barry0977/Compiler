declare void @print(ptr)
declare void @println(ptr)
declare void @printInt(i32)
declare void @printlnInt(i32)
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
@N = global i32 0
@M = global i32 0
@ch = global ptr null
@l = global ptr null
@r = global ptr null
@w = global ptr null
@.str.0 = private unnamed_addr constant [2 x i8] c" \00"
@.str.1 = private unnamed_addr constant [2 x i8] c"\0A\00"
define i32 @merge(i32 %x, i32 %y) {
entry:
	%x.1.1 = alloca i32;
	store i32 %x, ptr %x.1.1;
	%y.1.1 = alloca i32;
	store i32 %y, ptr %y.1.1;
	%0 = load i32, ptr %x.1.1;
	%1 = icmp eq i32 0, %0;
	%e.3.1 = alloca i32;
	%e.1.1 = alloca i32;
	br i1 %1, label %if.then.0,label %if.else.0;
if.then.0:
	%2 = load i32, ptr %y.1.1;
	ret i32 %2;
if.else.0:
	br label %if.end.0;
if.end.0:
	%3 = load i32, ptr %y.1.1;
	%4 = icmp eq i32 0, %3;
	br i1 %4, label %if.then.1,label %if.else.1;
if.then.1:
	%5 = load i32, ptr %x.1.1;
	ret i32 %5;
if.else.1:
	br label %if.end.1;
if.end.1:
	%6 = load ptr, ptr @w;
	%7 = load i32, ptr %x.1.1;
	%8 = getelementptr ptr, ptr %6, i32 %7;
	%9 = load i32, ptr %8;
	%10 = load ptr, ptr @w;
	%11 = load i32, ptr %y.1.1;
	%12 = getelementptr ptr, ptr %10, i32 %11;
	%13 = load i32, ptr %12;
	%14 = icmp slt i32 %9, %13;
	br i1 %14, label %if.then.2,label %if.else.2;
if.then.2:
	%15 = load i32, ptr %x.1.1;
	store i32 %15, ptr %e.3.1;
	%16 = load i32, ptr %x.1.1;
	%17 = load i32, ptr %y.1.1;
	store i32 %17, ptr %x.1.1;
	%18 = load i32, ptr %y.1.1;
	%19 = load i32, ptr %e.3.1;
	store i32 %19, ptr %y.1.1;
	br label %if.end.2;
if.else.2:
	br label %if.end.2;
if.end.2:
	%20 = load ptr, ptr @r;
	%21 = load i32, ptr %x.1.1;
	%22 = getelementptr ptr, ptr %20, i32 %21;
	%23 = load i32, ptr %22;
	%24 = load ptr, ptr @r;
	%25 = load i32, ptr %x.1.1;
	%26 = getelementptr ptr, ptr %24, i32 %25;
	%27 = load i32, ptr %26;
	%28 = load i32, ptr %y.1.1;
	%29 = call i32 @merge(i32 %27, i32 %28)
	store i32 %29, ptr %22;
	%30 = load ptr, ptr @l;
	%31 = load i32, ptr %x.1.1;
	%32 = getelementptr ptr, ptr %30, i32 %31;
	%33 = load i32, ptr %32;
	store i32 %33, ptr %e.1.1;
	%34 = load ptr, ptr @l;
	%35 = load i32, ptr %x.1.1;
	%36 = getelementptr ptr, ptr %34, i32 %35;
	%37 = load i32, ptr %36;
	%38 = load ptr, ptr @r;
	%39 = load i32, ptr %x.1.1;
	%40 = getelementptr ptr, ptr %38, i32 %39;
	%41 = load i32, ptr %40;
	store i32 %41, ptr %36;
	%42 = load ptr, ptr @r;
	%43 = load i32, ptr %x.1.1;
	%44 = getelementptr ptr, ptr %42, i32 %43;
	%45 = load i32, ptr %44;
	%46 = load i32, ptr %e.1.1;
	store i32 %46, ptr %44;
	%47 = load i32, ptr %x.1.1;
	ret i32 %47;
}

define i32 @main() {
entry:
	%0 = load i32, ptr @N;
	%1 = call i32 @getInt()
	store i32 %1, ptr @N;
	%2 = load i32, ptr @M;
	%3 = call i32 @getInt()
	store i32 %3, ptr @M;
	%4 = load ptr, ptr @ch;
	%5 = call ptr @getString()
	store ptr %5, ptr @ch;
	%6 = load ptr, ptr @l;
	%7 = load i32, ptr @N;
	%8 = load i32, ptr @M;
	%9 = add i32 %7, %8
	%10 = add i32 %9, 5
	%11 = call ptr @_malloc_array(i32 %10)
	store ptr %11, ptr @l;
	%12 = load ptr, ptr @r;
	%13 = load i32, ptr @N;
	%14 = load i32, ptr @M;
	%15 = add i32 %13, %14
	%16 = add i32 %15, 5
	%17 = call ptr @_malloc_array(i32 %16)
	store ptr %17, ptr @r;
	%18 = load ptr, ptr @w;
	%19 = load i32, ptr @N;
	%20 = load i32, ptr @M;
	%21 = add i32 %19, %20
	%22 = add i32 %21, 5
	%23 = call ptr @_malloc_array(i32 %22)
	store ptr %23, ptr @w;
	%i.1.2 = alloca i32;
	%24 = load i32, ptr %i.1.2;
	store i32 1, ptr %i.1.2;
	%rt0.1.2 = alloca i32;
	%rt1.1.2 = alloca i32;
	br label %for.cond.0;
for.cond.0:
	%25 = load i32, ptr %i.1.2;
	%26 = load i32, ptr @N;
	%27 = icmp sle i32 %25, %26;
	br i1 %27, label %for.body.0,label %for.end.0;
for.body.0:
	%28 = load ptr, ptr @w;
	%29 = load i32, ptr %i.1.2;
	%30 = getelementptr ptr, ptr %28, i32 %29;
	%31 = load i32, ptr %30;
	%32 = call i32 @getInt()
	store i32 %32, ptr %30;
	%33 = load ptr, ptr @l;
	%34 = load i32, ptr %i.1.2;
	%35 = getelementptr ptr, ptr %33, i32 %34;
	%36 = load i32, ptr %35;
	store i32 0, ptr %35;
	%37 = load ptr, ptr @r;
	%38 = load i32, ptr %i.1.2;
	%39 = getelementptr ptr, ptr %37, i32 %38;
	%40 = load i32, ptr %39;
	store i32 0, ptr %39;
	br label %for.step.0;
for.step.0:
	%41 = load i32, ptr %i.1.2;
	%42 = add i32 %41, 1
	store i32 %42, ptr %i.1.2;
	br label %for.cond.0;
for.end.0:
	%43 = load i32, ptr %i.1.2;
	store i32 1, ptr %i.1.2;
	br label %for.cond.1;
for.cond.1:
	%44 = load i32, ptr %i.1.2;
	%45 = load i32, ptr @M;
	%46 = icmp sle i32 %44, %45;
	br i1 %46, label %for.body.1,label %for.end.1;
for.body.1:
	%47 = load ptr, ptr @w;
	%48 = load i32, ptr %i.1.2;
	%49 = load i32, ptr @N;
	%50 = add i32 %48, %49
	%51 = getelementptr ptr, ptr %47, i32 %50;
	%52 = load i32, ptr %51;
	%53 = load ptr, ptr @ch;
	%54 = load i32, ptr %i.1.2;
	%55 = sub i32 %54, 1
	%56 = call i32 @string.ord(ptr %53, i32 %55)
	store i32 %56, ptr %51;
	%57 = load ptr, ptr @l;
	%58 = load i32, ptr %i.1.2;
	%59 = load i32, ptr @N;
	%60 = add i32 %58, %59
	%61 = getelementptr ptr, ptr %57, i32 %60;
	%62 = load i32, ptr %61;
	store i32 0, ptr %61;
	%63 = load ptr, ptr @r;
	%64 = load i32, ptr %i.1.2;
	%65 = load i32, ptr @N;
	%66 = add i32 %64, %65
	%67 = getelementptr ptr, ptr %63, i32 %66;
	%68 = load i32, ptr %67;
	store i32 0, ptr %67;
	br label %for.step.1;
for.step.1:
	%69 = load i32, ptr %i.1.2;
	%70 = add i32 %69, 1
	store i32 %70, ptr %i.1.2;
	br label %for.cond.1;
for.end.1:
	store i32 1, ptr %rt0.1.2;
	%71 = load i32, ptr @N;
	%72 = add i32 %71, 1
	store i32 %72, ptr %rt1.1.2;
	%73 = load i32, ptr %i.1.2;
	store i32 2, ptr %i.1.2;
	br label %for.cond.2;
for.cond.2:
	%74 = load i32, ptr %i.1.2;
	%75 = load i32, ptr @N;
	%76 = icmp sle i32 %74, %75;
	br i1 %76, label %for.body.2,label %for.end.2;
for.body.2:
	%77 = load i32, ptr %rt0.1.2;
	%78 = load i32, ptr %rt0.1.2;
	%79 = load i32, ptr %i.1.2;
	%80 = call i32 @merge(i32 %78, i32 %79)
	store i32 %80, ptr %rt0.1.2;
	br label %for.step.2;
for.step.2:
	%81 = load i32, ptr %i.1.2;
	%82 = add i32 %81, 1
	store i32 %82, ptr %i.1.2;
	br label %for.cond.2;
for.end.2:
	%83 = load i32, ptr %i.1.2;
	%84 = load i32, ptr @N;
	%85 = add i32 %84, 2
	store i32 %85, ptr %i.1.2;
	br label %for.cond.3;
for.cond.3:
	%86 = load i32, ptr %i.1.2;
	%87 = load i32, ptr @N;
	%88 = load i32, ptr @M;
	%89 = add i32 %87, %88
	%90 = icmp sle i32 %86, %89;
	br i1 %90, label %for.body.3,label %for.end.3;
for.body.3:
	%91 = load i32, ptr %rt1.1.2;
	%92 = load i32, ptr %rt1.1.2;
	%93 = load i32, ptr %i.1.2;
	%94 = call i32 @merge(i32 %92, i32 %93)
	store i32 %94, ptr %rt1.1.2;
	br label %for.step.3;
for.step.3:
	%95 = load i32, ptr %i.1.2;
	%96 = add i32 %95, 1
	store i32 %96, ptr %i.1.2;
	br label %for.cond.3;
for.end.3:
	%97 = load ptr, ptr @w;
	%98 = load i32, ptr %rt0.1.2;
	%99 = getelementptr ptr, ptr %97, i32 %98;
	%100 = load i32, ptr %99;
	%101 = call ptr @toString(i32 %100)
	call void @print(ptr %101)
	call void @print(ptr @.str.0)
	%102 = load ptr, ptr @ch;
	%103 = load i32, ptr %rt1.1.2;
	%104 = load i32, ptr @N;
	%105 = sub i32 %103, %104
	%106 = sub i32 %105, 1
	%107 = load i32, ptr %rt1.1.2;
	%108 = load i32, ptr @N;
	%109 = sub i32 %107, %108
	%110 = call ptr @string.substring(ptr %102, i32 %106, i32 %109)
	call void @print(ptr %110)
	call void @print(ptr @.str.1)
	%111 = load i32, ptr %rt0.1.2;
	%112 = load i32, ptr %rt1.1.2;
	%113 = call i32 @merge(i32 %111, i32 %112)
	%114 = call ptr @toString(i32 %113)
	call void @println(ptr %114)
	ret i32 0;
}

