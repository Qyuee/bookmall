use bookmall;

-- 카테고리
insert into category values(null, 'SF/공상과학');
insert into category values(null, '멜로/로맨스');
insert into category values(null, '판타지');

select cateno, name from category;

-- 도서
insert into book values(null, '우주어딘가에서', 10000, 1);
insert into book values(null, '인간, 달에 가다', 12000, 1);
insert into book values(null, '해를 품은 달(1권)', 15000, 2);
insert into book values(null, '해를 품은 달(2권)', 17000, 2);
insert into book values(null, '해리포터와 마법사의 돌', 20000, 3);
insert into book values(null, '해리포터-불사조 기사단', 19000, 3);


select *
from book b, category c
where (b.cateno=c.cateno);

delete 
from book;


-- 회원
insert into member values(null, '이동석', '010-9007-4231', 'lee33398@naver.com', '1234');
insert into member values(null, '홍길동', '010-8787-1212', 'honggil@naver.com', '1234');

select *
from member;

-- 로그인
select memno
from member
where name='이동' and password='1234';

-- 상황 1. 이동석이 '우주어딘가에서' - 2권, '해리포터와 마법사의 돌'- 1권을 장바구니에 담는다.
insert into cart values(concat(date_format(sysdate(), '%y%m%d%H%i%s'), '1'), 2, 20000, 1, 1);
insert into cart values(concat(date_format(sysdate(), '%y%m%d%H%i%s'), '1'), 1, 12000, 1, 2);

desc cart;

select count(*) from cart where cartno='C31';
select count(*) from cart where bookno= 3 and memno= 1; 

update cart
set qty=100
where bookno=3 and memno=1;

select *
from cart;

delete from cart
where memno=1;

desc order_book;

-- 이동석이 카트에 담은 책의 정보?
select *
from cart c, book b
where (c.bookno=b.bookno)
	and c.memno=1;
    
select m.name, b.title, b.price, c.qty, (c.qty*b.price) as 'sub_total'
from book b, cart c, member m
where (b.bookno=c.bookno) and (m.memno=c.memno) and c.memno=1;

select *
from `order`;

delete from order_book;

delete from `order`;

select *
from order_book;

select ob.bookno
from order_book ob
where ob.orderno=(
	select o.orderno
	from member m, `order` o, order_book ob
	where (m.memno=o.memno)
		and (o.orderno=ob.orderno)
		and m.memno=1
);

select distinct m.name, o.orderno,  o.addr, o.price
from member m, `order` o
where (m.memno=o.memno)
	and m.memno=1;

select orderno, sum(qty*price)
from order_book
where orderno='OD15207467272631';

update `order`
set price=(
	select sum(qty*price)
	from order_book
	where orderno='OD15207467272631'
)
where orderno='OD15207467272631';

-- 주문 후 카트 삭제.
delete from cart
where memno=1;

-- 주문 내역 확인
select ob.bookno, b.title, ob.price, ob.qty
from order_book ob, book b
where (ob.bookno=b.bookno)
	and ob.orderno='OD15207536546031';