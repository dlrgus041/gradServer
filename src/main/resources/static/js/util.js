const form = document.getElementById("form");
const address2 = document.getElementById("address2");

const table = [
    [
        "시/군/구"
    ], [
        "'군/구' 선택", "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"
    ], [
        "'군/구' 선택", "광산구", "남구", "동구", "북구", "서구"
    ], [
        "'군/구' 선택", "남구", "달서구", "동구", "북구", "서구", "수성구", "중구"
    ], [
        "'군/구' 선택", "대덕구", "동구", "서구", "유성구", "중구"
    ], [
        "'군/구' 선택", "강서구", "금정구", "기장군", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구"
    ], [
        "'군/구' 선택", "남구", "동구", "북구", "울주군", "중구"
    ], [
        "'군/구' 선택", "강화구", "계양구", "남동구", "동구", "미추홀구", "부평구", "서구", "연수구", "옹진군", "중구"
    ], [
        "선택", "세종시"
    ], [
        "'시/군' 선택", "강릉", "고성", "동해", "삼척", "속초", "양구", "양양", "영월", "원주", "인제", "정원", "철원", "춘천", "태백", "평창", "홍천", "화천", "횡성"
    ], [
        "'시/군' 선택", "가평", "고양", "과천", "광명", "광주", "구리", "군포", "김포", "남양주", "동두천", "부천", "성남", "수원", "시흥", "안산", "안성", "안양", "양주", "양평", "여주", "연천", "오신", "용인", "의왕", "의정부", "이천", "파주", "평택", "포천", "하남", "화성"
    ], [
        "'시/군' 선택", "거제", "거창", "고성", "김해", "남해", "밀양", "사천", "산청", "양산", "의령", "진주", "창녕", "창원", "통영", "하동", "함안", "함양", "합천"
    ], [
        "'시/군' 선택", "경산", "경주", "고령", "구미", "군위", "김천", "문경", "봉화", "상주", "성주", "안동", "영덕", "영양", "영주", "영천", "예천", "울릉", "울진", "의성", "청도", "청송", "칠곡", "포항"
    ], [
        "'시/군' 선택", "강진", "고흥", "곡성", "광양", "구례", "나주", "담양", "목포", "무안", "보성", "순천", "신안", "여수", "영광", "영암", "완도", "장성", "장흥", "진도", "함평", "해남", "화순"
    ], [
        "'시/군' 선택", "고창", "군산", "김제", "남원", "무주", "부안", "순창", "완주", "익산", "임실", "장수", "전주", "정읍", "진안"
    ], [
        "'시/군' 선택", "계룡", "공주", "금산", "논산", "당진", "보령", "부여", "서산", "서천", "아산", "예산", "천안", "청양", "태안", "홍성"
    ], [
        "'시/군' 선택", "괴산", "단양", "보은", "영동", "옥천", "음성", "제천", "증평", "진천", "청주", "충주"
    ], [
        "서귀포", "제주"
    ]
];

function searchForm() {
    form.children[1].value = null;
    form.children[1].disabled = (form.children[0].options[form.children[0].selectedIndex].value === "blank");
}

function deleteElement(flag, id) {
    if (confirm('해당 항목을 삭제하겠습니까?')) {
        alert('삭제되었습니다.\n초기 화면으로 돌아갑니다.');
        location.href = (flag ? '/employee' : '/visitor') + '/delete/' + id;
    }
}

function switchPage(flag) {
    if (confirm((flag > 0 ? '직원 관리' : flag < 0 ? '방문자 관리' : '출입자 목록') + ' 페이지로 이동합니다.\n(주의 : 완료되지 않은 작업은 저장되지 않습니다.)')) {
        location.href = flag > 0 ? '/employee' : flag < 0 ? '/visitor' : '/log'
    }
}

function selectAddress1(code1) {

    address2.removeChild(address2.children[0]);
    let el = document.createElement("select");
    el.name = "address2";
    el.style = "width: -webkit-fill-available;";

    for (let x in table[code1]) {
        let opt = document.createElement("option");
        opt.value = x;
        opt.innerText = table[code1][x];
        el.appendChild(opt);
    }

    address2.appendChild(el);
}

function kakao(login, status) {
    if (login) {
        if (status) alert('이미 로그인되었습니다.');
        else location.href='/refresh';
    }
    else location.href='https://kauth.kakao.com/oauth/authorize?client_id=55be62f097c7e69a2ff9b58382f72259&redirect_uri=http://uouitcparkjh1998.ddns.net:8080/login&response_type=code&scope=profile_nickname,profile_image,friends,talk_message';
}