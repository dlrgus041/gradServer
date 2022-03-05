function fun(flag) {
    if (confirm((flag ? '직원 관리 페이지로 이동합니다.' : '방문자 관리 페이지로 이동합니다.')
        + '\n(주의) 완료되지 않은 작업은 저장되지 않습니다.'))
        location.href=flag ? '/employee' : '/visitor';
}