select sum(ACTUAL_DAYS) from minoas.slavikos.WORK_EXPERIENCE 
where IS_ACTIVE=1 and EDUCATIONAL=1 and EMPLOYEE_ID=8879


select sum(ACTUAL_DAYS) from minoas.slavikos.WORK_EXPERIENCE 
where IS_ACTIVE=1 and EMPLOYEE_ID=8879


select * from minoas.slavikos.EMPLOYEE where id = 7507


select * from minoas.slavikos.PENALTY

select * from minoas.slavikos.EMPLOYEE where ID=8879
select * from minoas.slavikos.EMPLOYEE_INFO where EMPLOYEE_ID=8879
select * from minoas.slavikos.RANK_INFO where EMPLOYEE_INFO_ID=8510


select * from minoas.slavikos.WORK_EXPERIENCE where EMPLOYEE_ID=8879



select distinct COMMENT from minoas.slavikos.WORK_EXPERIENCE where EXPERIENCE_TYPE_ID in(3, 4)
and ( COMMENT not like '%>>%' and COMMENT not like '%���%' and COMMENT not like '%���%'
and COMMENT not like '%���%' and COMMENT not like '%����%' and COMMENT not like '%�����%'
and COMMENT not like '%���%' and COMMENT not like '%�/���%' and COMMENT not like '%���%' and COMMENT not like '%���%'
and COMMENT not like '%���%' and COMMENT not like '%����%' and COMMENT not like '%�.�.%'
and COMMENT not like '%��%' and COMMENT not like '%�� %' and COMMENT not like '%�� %'
and COMMENT not like '%���%' and COMMENT not like '%�.��%' and COMMENT not like '%�.�.�%'
and COMMENT not like '%�.�.�%' and COMMENT not like '%����%' and COMMENT not like '%������%'
and COMMENT not like '%�.�.�%' and COMMENT not like '%����%' and COMMENT not like '%�� %'
and COMMENT not like '%��� %' and COMMENT not like '%�.�.�%' and COMMENT not like '%�����%'
and COMMENT not like '%�������%' and COMMENT not like '%��� %' and COMMENT not like '%���%'
and COMMENT not like '%�����%' and COMMENT not like '%�� %' and COMMENT not like '%�����%'
and COMMENT not like '%���/���%' and COMMENT not like '%�����%' and COMMENT not like '%���%'
and COMMENT not like '%������%' and COMMENT not like '%�.�.�%' and COMMENT not like '%�.%'
and COMMENT not like '%�.�.�.�%' and COMMENT not like '%��� %' and COMMENT not like '%�����%'
and COMMENT not like '%��. %' and COMMENT not like '%�. �.%' and COMMENT not like '%�.�%'
and COMMENT not like '%���/���%' and COMMENT not like '%�/���%' and COMMENT not like '%���.%'
and COMMENT not like '%�� %' and COMMENT not like '%�.%' and COMMENT not like '%�/����%')

order by comment

select * from minoas.slavikos.WORK_EXPERIENCE where EXPERIENCE_TYPE_ID in(3, 4)
and ( COMMENT like '%���%' or COMMENT like '%�.�.�.%' or COMMENT like '%�����%' or COMMENT like '%����%'
or COMMENT like '%�.�.�.�.%' or COMMENT like '%���%' or COMMENT like '%�.�.�.%')


select * from minoas.slavikos.WORK_EXPERIENCE where EXPERIENCE_TYPE_ID = 8


update minoas.slavikos.WORK_EXPERIENCE set TEACHING=1 where EXPERIENCE_TYPE_ID = 8