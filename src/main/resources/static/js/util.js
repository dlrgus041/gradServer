function searchForm() {
    const form = document.getElementById("form");
    form.children[1].value = null;
    form.children[1].disabled = (form.children[0].options[form.children[0].selectedIndex].value === "blank");
    // if (form.children[0].options[form.children[0].selectedIndex].value === "vaccine") {
    //     form.children[1].hidden = true;
    //     form.children[2].hidden = false;
    // } else {
    //     form.children[1].hidden = false;
    //     form.children[2].hidden = true;
    // }
}

function deleteElement(flag, id) {
    if (confirm('해당 항목을 삭제하겠습니까?')) {
        alert('삭제되었습니다.\n초기 화면으로 돌아갑니다.');
        location.href = (flag ? '/employee' : '/visitor') + '/delete/' + id;
    }
}

function switchPage(flag) {
    if (confirm((flag > 0 ? '직원 관리' : flag < 0 ? '방문자 관리' : '출입자 목록') + ' 페이지로 이동합니다.\n(주의 : 완료되지 않은 작업은 저장되지 않습니다.)')) {
        location.href = flag > 0 ? '/employee' : flag < 0 ? '/visitor' : '/entry'
    }
}