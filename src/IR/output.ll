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
%class.point = type {i32, i32, i32 }
@.str.0 = private unnamed_addr constant [2 x i8] c"(\00"
@.str.1 = private unnamed_addr constant [3 x i8] c", \00"
@.str.2 = private unnamed_addr constant [3 x i8] c", \00"
@.str.3 = private unnamed_addr constant [2 x i8] c")\00"
define void @point.set(ptr %this, i32 %a_x, i32 %a_y, i32 %a_z) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%a_x.2.1 = alloca i32;
	store i32 %a_x, ptr %a_x.2.1;
	%a_y.2.1 = alloca i32;
	store i32 %a_y, ptr %a_y.2.1;
	%a_z.2.1 = alloca i32;
	store i32 %a_z, ptr %a_z.2.1;
	%0 = getelementptr %class.point, ptr %this.this, i32 0, i32 0;
	%1 = load i32, ptr %0;
	%2 = load i32, ptr %a_x.2.1;
	store i32 %2, ptr %0;
	%3 = getelementptr %class.point, ptr %this.this, i32 0, i32 1;
	%4 = load i32, ptr %3;
	%5 = load i32, ptr %a_y.2.1;
	store i32 %5, ptr %3;
	%6 = getelementptr %class.point, ptr %this.this, i32 0, i32 2;
	%7 = load i32, ptr %6;
	%8 = load i32, ptr %a_z.2.1;
	store i32 %8, ptr %6;
	ret void;
}

define i32 @point.sqrLen(ptr %this) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%0 = getelementptr %class.point, ptr %this.this, i32 0, i32 0;
	%1 = load i32, ptr %0;
	%2 = getelementptr %class.point, ptr %this.this, i32 0, i32 0;
	%3 = load i32, ptr %2;
	%4 = mul i32 %1, %3
	%5 = getelementptr %class.point, ptr %this.this, i32 0, i32 1;
	%6 = load i32, ptr %5;
	%7 = getelementptr %class.point, ptr %this.this, i32 0, i32 1;
	%8 = load i32, ptr %7;
	%9 = mul i32 %6, %8
	%10 = add i32 %4, %9
	%11 = getelementptr %class.point, ptr %this.this, i32 0, i32 2;
	%12 = load i32, ptr %11;
	%13 = getelementptr %class.point, ptr %this.this, i32 0, i32 2;
	%14 = load i32, ptr %13;
	%15 = mul i32 %12, %14
	%16 = add i32 %10, %15
	ret i32 %16;
}

define i32 @point.sqrDis(ptr %this, ptr %other) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%other.2.3 = alloca ptr;
	store ptr %other, ptr %other.2.3;
	%0 = getelementptr %class.point, ptr %this.this, i32 0, i32 0;
	%1 = load i32, ptr %0;
	%2 = load ptr, ptr %other.2.3;
	%3 = getelementptr %class.point, ptr %2, i32 0, i32 0;
	%4 = load i32, ptr %3;
	%5 = sub i32 %1, %4
	%6 = getelementptr %class.point, ptr %this.this, i32 0, i32 0;
	%7 = load i32, ptr %6;
	%8 = load ptr, ptr %other.2.3;
	%9 = getelementptr %class.point, ptr %8, i32 0, i32 0;
	%10 = load i32, ptr %9;
	%11 = sub i32 %7, %10
	%12 = mul i32 %5, %11
	%13 = getelementptr %class.point, ptr %this.this, i32 0, i32 1;
	%14 = load i32, ptr %13;
	%15 = load ptr, ptr %other.2.3;
	%16 = getelementptr %class.point, ptr %15, i32 0, i32 1;
	%17 = load i32, ptr %16;
	%18 = sub i32 %14, %17
	%19 = getelementptr %class.point, ptr %this.this, i32 0, i32 1;
	%20 = load i32, ptr %19;
	%21 = load ptr, ptr %other.2.3;
	%22 = getelementptr %class.point, ptr %21, i32 0, i32 1;
	%23 = load i32, ptr %22;
	%24 = sub i32 %20, %23
	%25 = mul i32 %18, %24
	%26 = add i32 %12, %25
	%27 = getelementptr %class.point, ptr %this.this, i32 0, i32 2;
	%28 = load i32, ptr %27;
	%29 = load ptr, ptr %other.2.3;
	%30 = getelementptr %class.point, ptr %29, i32 0, i32 2;
	%31 = load i32, ptr %30;
	%32 = sub i32 %28, %31
	%33 = getelementptr %class.point, ptr %this.this, i32 0, i32 2;
	%34 = load i32, ptr %33;
	%35 = load ptr, ptr %other.2.3;
	%36 = getelementptr %class.point, ptr %35, i32 0, i32 2;
	%37 = load i32, ptr %36;
	%38 = sub i32 %34, %37
	%39 = mul i32 %32, %38
	%40 = add i32 %26, %39
	ret i32 %40;
}

define i32 @point.dot(ptr %this, ptr %other) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%other.2.4 = alloca ptr;
	store ptr %other, ptr %other.2.4;
	%0 = getelementptr %class.point, ptr %this.this, i32 0, i32 0;
	%1 = load i32, ptr %0;
	%2 = load ptr, ptr %other.2.4;
	%3 = getelementptr %class.point, ptr %2, i32 0, i32 0;
	%4 = load i32, ptr %3;
	%5 = mul i32 %1, %4
	%6 = getelementptr %class.point, ptr %this.this, i32 0, i32 1;
	%7 = load i32, ptr %6;
	%8 = load ptr, ptr %other.2.4;
	%9 = getelementptr %class.point, ptr %8, i32 0, i32 1;
	%10 = load i32, ptr %9;
	%11 = mul i32 %7, %10
	%12 = add i32 %5, %11
	%13 = getelementptr %class.point, ptr %this.this, i32 0, i32 2;
	%14 = load i32, ptr %13;
	%15 = load ptr, ptr %other.2.4;
	%16 = getelementptr %class.point, ptr %15, i32 0, i32 2;
	%17 = load i32, ptr %16;
	%18 = mul i32 %14, %17
	%19 = add i32 %12, %18
	ret i32 %19;
}

define ptr @point.cross(ptr %this, ptr %other) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%other.2.5 = alloca ptr;
	store ptr %other, ptr %other.2.5;
	%retval.2.5 = alloca ptr;
	%0 = call ptr @_malloc(i32 3)
	store ptr %0, ptr %retval.2.5;
	%1 = load ptr, ptr %retval.2.5;
	%2 = getelementptr %class.point, ptr %this.this, i32 0, i32 1;
	%3 = load i32, ptr %2;
	%4 = load ptr, ptr %other.2.5;
	%5 = getelementptr %class.point, ptr %4, i32 0, i32 2;
	%6 = load i32, ptr %5;
	%7 = mul i32 %3, %6
	%8 = getelementptr %class.point, ptr %this.this, i32 0, i32 2;
	%9 = load i32, ptr %8;
	%10 = load ptr, ptr %other.2.5;
	%11 = getelementptr %class.point, ptr %10, i32 0, i32 1;
	%12 = load i32, ptr %11;
	%13 = mul i32 %9, %12
	%14 = sub i32 %7, %13
	%15 = getelementptr %class.point, ptr %this.this, i32 0, i32 2;
	%16 = load i32, ptr %15;
	%17 = load ptr, ptr %other.2.5;
	%18 = getelementptr %class.point, ptr %17, i32 0, i32 0;
	%19 = load i32, ptr %18;
	%20 = mul i32 %16, %19
	%21 = getelementptr %class.point, ptr %this.this, i32 0, i32 0;
	%22 = load i32, ptr %21;
	%23 = load ptr, ptr %other.2.5;
	%24 = getelementptr %class.point, ptr %23, i32 0, i32 2;
	%25 = load i32, ptr %24;
	%26 = mul i32 %22, %25
	%27 = sub i32 %20, %26
	%28 = getelementptr %class.point, ptr %this.this, i32 0, i32 0;
	%29 = load i32, ptr %28;
	%30 = load ptr, ptr %other.2.5;
	%31 = getelementptr %class.point, ptr %30, i32 0, i32 1;
	%32 = load i32, ptr %31;
	%33 = mul i32 %29, %32
	%34 = getelementptr %class.point, ptr %this.this, i32 0, i32 1;
	%35 = load i32, ptr %34;
	%36 = load ptr, ptr %other.2.5;
	%37 = getelementptr %class.point, ptr %36, i32 0, i32 0;
	%38 = load i32, ptr %37;
	%39 = mul i32 %35, %38
	%40 = sub i32 %33, %39
	call void @point.set(ptr %1, i32 %14, i32 %27, i32 %40)
	%41 = load ptr, ptr %retval.2.5;
	ret ptr %41;
}

define ptr @point.add(ptr %this, ptr %other) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%other.2.6 = alloca ptr;
	store ptr %other, ptr %other.2.6;
	%0 = getelementptr %class.point, ptr %this.this, i32 0, i32 0;
	%1 = load i32, ptr %0;
	%2 = getelementptr %class.point, ptr %this.this, i32 0, i32 0;
	%3 = load i32, ptr %2;
	%4 = load ptr, ptr %other.2.6;
	%5 = getelementptr %class.point, ptr %4, i32 0, i32 0;
	%6 = load i32, ptr %5;
	%7 = add i32 %3, %6
	store i32 %7, ptr %0;
	%8 = getelementptr %class.point, ptr %this.this, i32 0, i32 1;
	%9 = load i32, ptr %8;
	%10 = getelementptr %class.point, ptr %this.this, i32 0, i32 1;
	%11 = load i32, ptr %10;
	%12 = load ptr, ptr %other.2.6;
	%13 = getelementptr %class.point, ptr %12, i32 0, i32 1;
	%14 = load i32, ptr %13;
	%15 = add i32 %11, %14
	store i32 %15, ptr %8;
	%16 = getelementptr %class.point, ptr %this.this, i32 0, i32 2;
	%17 = load i32, ptr %16;
	%18 = getelementptr %class.point, ptr %this.this, i32 0, i32 2;
	%19 = load i32, ptr %18;
	%20 = load ptr, ptr %other.2.6;
	%21 = getelementptr %class.point, ptr %20, i32 0, i32 2;
	%22 = load i32, ptr %21;
	%23 = add i32 %19, %22
	store i32 %23, ptr %16;
	ret ptr %this.this;
}

define ptr @point.sub(ptr %this, ptr %other) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%other.2.7 = alloca ptr;
	store ptr %other, ptr %other.2.7;
	%0 = getelementptr %class.point, ptr %this.this, i32 0, i32 0;
	%1 = load i32, ptr %0;
	%2 = getelementptr %class.point, ptr %this.this, i32 0, i32 0;
	%3 = load i32, ptr %2;
	%4 = load ptr, ptr %other.2.7;
	%5 = getelementptr %class.point, ptr %4, i32 0, i32 0;
	%6 = load i32, ptr %5;
	%7 = sub i32 %3, %6
	store i32 %7, ptr %0;
	%8 = getelementptr %class.point, ptr %this.this, i32 0, i32 1;
	%9 = load i32, ptr %8;
	%10 = getelementptr %class.point, ptr %this.this, i32 0, i32 1;
	%11 = load i32, ptr %10;
	%12 = load ptr, ptr %other.2.7;
	%13 = getelementptr %class.point, ptr %12, i32 0, i32 1;
	%14 = load i32, ptr %13;
	%15 = sub i32 %11, %14
	store i32 %15, ptr %8;
	%16 = getelementptr %class.point, ptr %this.this, i32 0, i32 2;
	%17 = load i32, ptr %16;
	%18 = getelementptr %class.point, ptr %this.this, i32 0, i32 2;
	%19 = load i32, ptr %18;
	%20 = load ptr, ptr %other.2.7;
	%21 = getelementptr %class.point, ptr %20, i32 0, i32 2;
	%22 = load i32, ptr %21;
	%23 = sub i32 %19, %22
	store i32 %23, ptr %16;
	ret ptr %this.this;
}

define void @point.printPoint(ptr %this) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%0 = getelementptr %class.point, ptr %this.this, i32 0, i32 0;
	%1 = load i32, ptr %0;
	%2 = call ptr @toString(i32 %1)
	%3 = call ptr @string.add(ptr @.str.0, ptr %2)
	%4 = call ptr @string.add(ptr %3, ptr @.str.1)
	%5 = getelementptr %class.point, ptr %this.this, i32 0, i32 1;
	%6 = load i32, ptr %5;
	%7 = call ptr @toString(i32 %6)
	%8 = call ptr @string.add(ptr %4, ptr %7)
	%9 = call ptr @string.add(ptr %8, ptr @.str.2)
	%10 = getelementptr %class.point, ptr %this.this, i32 0, i32 2;
	%11 = load i32, ptr %10;
	%12 = call ptr @toString(i32 %11)
	%13 = call ptr @string.add(ptr %9, ptr %12)
	%14 = call ptr @string.add(ptr %13, ptr @.str.3)
	call void @println(ptr %14)
	ret void;
}

define void @point.point(ptr %this) {
entry:
	%this.addr = alloca ptr;
	store ptr %this, ptr %this.addr;
	%this.this = load ptr, ptr %this.addr;
	%0 = getelementptr %class.point, ptr %this.this, i32 0, i32 0;
	%1 = load i32, ptr %0;
	store i32 0, ptr %0;
	%2 = getelementptr %class.point, ptr %this.this, i32 0, i32 1;
	%3 = load i32, ptr %2;
	store i32 0, ptr %2;
	%4 = getelementptr %class.point, ptr %this.this, i32 0, i32 2;
	%5 = load i32, ptr %4;
	store i32 0, ptr %4;
	ret void;
}

define i32 @main() {
entry:
	%a.1.2 = alloca ptr;
	%0 = call ptr @_malloc(i32 3)
	store ptr %0, ptr %a.1.2;
	%b.1.2 = alloca ptr;
	%1 = call ptr @_malloc(i32 3)
	store ptr %1, ptr %b.1.2;
	%c.1.2 = alloca ptr;
	%2 = call ptr @_malloc(i32 3)
	store ptr %2, ptr %c.1.2;
	%d.1.2 = alloca ptr;
	%3 = call ptr @_malloc(i32 3)
	store ptr %3, ptr %d.1.2;
	%4 = load ptr, ptr %a.1.2;
	call void @point.printPoint(ptr %4)
	%5 = load ptr, ptr %a.1.2;
	%6 = sub i32 0, 463
	call void @point.set(ptr %5, i32 849, i32 %6, i32 480)
	%7 = load ptr, ptr %b.1.2;
	%8 = sub i32 0, 208
	%9 = sub i32 0, 150
	call void @point.set(ptr %7, i32 %8, i32 585, i32 %9)
	%10 = load ptr, ptr %c.1.2;
	%11 = sub i32 0, 670
	%12 = sub i32 0, 742
	call void @point.set(ptr %10, i32 360, i32 %11, i32 %12)
	%13 = load ptr, ptr %d.1.2;
	%14 = sub i32 0, 29
	%15 = sub i32 0, 591
	%16 = sub i32 0, 960
	call void @point.set(ptr %13, i32 %14, i32 %15, i32 %16)
	%17 = load ptr, ptr %a.1.2;
	%18 = load ptr, ptr %b.1.2;
	%19 = call ptr @point.add(ptr %17, ptr %18)
	%20 = load ptr, ptr %b.1.2;
	%21 = load ptr, ptr %c.1.2;
	%22 = call ptr @point.add(ptr %20, ptr %21)
	%23 = load ptr, ptr %d.1.2;
	%24 = load ptr, ptr %c.1.2;
	%25 = call ptr @point.add(ptr %23, ptr %24)
	%26 = load ptr, ptr %c.1.2;
	%27 = load ptr, ptr %a.1.2;
	%28 = call ptr @point.sub(ptr %26, ptr %27)
	%29 = load ptr, ptr %b.1.2;
	%30 = load ptr, ptr %d.1.2;
	%31 = call ptr @point.sub(ptr %29, ptr %30)
	%32 = load ptr, ptr %d.1.2;
	%33 = load ptr, ptr %c.1.2;
	%34 = call ptr @point.sub(ptr %32, ptr %33)
	%35 = load ptr, ptr %c.1.2;
	%36 = load ptr, ptr %b.1.2;
	%37 = call ptr @point.add(ptr %35, ptr %36)
	%38 = load ptr, ptr %a.1.2;
	%39 = load ptr, ptr %b.1.2;
	%40 = call ptr @point.add(ptr %38, ptr %39)
	%41 = load ptr, ptr %b.1.2;
	%42 = load ptr, ptr %b.1.2;
	%43 = call ptr @point.add(ptr %41, ptr %42)
	%44 = load ptr, ptr %c.1.2;
	%45 = load ptr, ptr %c.1.2;
	%46 = call ptr @point.add(ptr %44, ptr %45)
	%47 = load ptr, ptr %a.1.2;
	%48 = load ptr, ptr %d.1.2;
	%49 = call ptr @point.sub(ptr %47, ptr %48)
	%50 = load ptr, ptr %a.1.2;
	%51 = load ptr, ptr %b.1.2;
	%52 = call ptr @point.add(ptr %50, ptr %51)
	%53 = load ptr, ptr %b.1.2;
	%54 = load ptr, ptr %c.1.2;
	%55 = call ptr @point.sub(ptr %53, ptr %54)
	%56 = load ptr, ptr %a.1.2;
	%57 = call i32 @point.sqrLen(ptr %56)
	%58 = call ptr @toString(i32 %57)
	call void @println(ptr %58)
	%59 = load ptr, ptr %b.1.2;
	%60 = call i32 @point.sqrLen(ptr %59)
	%61 = call ptr @toString(i32 %60)
	call void @println(ptr %61)
	%62 = load ptr, ptr %b.1.2;
	%63 = load ptr, ptr %c.1.2;
	%64 = call i32 @point.sqrDis(ptr %62, ptr %63)
	%65 = call ptr @toString(i32 %64)
	call void @println(ptr %65)
	%66 = load ptr, ptr %d.1.2;
	%67 = load ptr, ptr %a.1.2;
	%68 = call i32 @point.sqrDis(ptr %66, ptr %67)
	%69 = call ptr @toString(i32 %68)
	call void @println(ptr %69)
	%70 = load ptr, ptr %c.1.2;
	%71 = load ptr, ptr %a.1.2;
	%72 = call i32 @point.dot(ptr %70, ptr %71)
	%73 = call ptr @toString(i32 %72)
	call void @println(ptr %73)
	%74 = load ptr, ptr %b.1.2;
	%75 = load ptr, ptr %d.1.2;
	%76 = call ptr @point.cross(ptr %74, ptr %75)
	call void @point.printPoint(ptr %76)
	%77 = load ptr, ptr %a.1.2;
	call void @point.printPoint(ptr %77)
	%78 = load ptr, ptr %b.1.2;
	call void @point.printPoint(ptr %78)
	%79 = load ptr, ptr %c.1.2;
	call void @point.printPoint(ptr %79)
	%80 = load ptr, ptr %d.1.2;
	call void @point.printPoint(ptr %80)
	ret i32 0;
}

