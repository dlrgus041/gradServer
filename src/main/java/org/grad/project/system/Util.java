package org.grad.project.system;

import org.grad.project.entry.Entry;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Util {

    private static final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static PrintWriter writer;

    static {
        try {
            writer = new PrintWriter("log.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String codeToAddress(int code) {
        code -= 1000;
        return codeToAddress(code / 100, code % 100);
    }

    public static String codeToAddress(int code1, int code2) {
        return table[0][code1 - 1] + " " + table[code1][code2 - 1];
    }

    public static int addressToCode(String address) {

        String[] temp = address.split(" ");
        int code1 = 0, code2 = 0;

        for (int i = 0; i < table[0].length; i++) {
            if (Objects.equals(table[0][i], temp[0])) {
                code1 = ++i;
                break;
            }
        }

        code2 = Arrays.binarySearch(table[code1], temp[1]) + 1;
        return 100 * code1 + code2;
    }

    private static final String[][] table = {
            {
                "서울특별시", "광주광역시", "대구광역시", "대전광역시", "부산광역시", "울산광역시", "인천광역시", "세종특별자치시", "강원도", "경기도", "경상남도", "경상북도", "전라남도", "전라북도", "충청남도", "충청북도", "제주도"
            }, {
                "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"
            }, {
                "광산구", "남구", "동구", "북구", "서구"
            }, {
                "남구", "달서구", "동구", "북구", "서구", "수성구", "중구"
            }, {
                "대덕구", "동구", "서구", "유성구", "중구"
            }, {
                "강서구", "금정구", "기장군", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구"
            }, {
                "남구", "동구", "북구", "울주군", "중구"
            }, {
                "강화구", "계양구", "남동구", "동구", "미추홀구", "부평구", "서구", "연수구", "옹진군", "중구"
            }, {
                "세종시"
            }, {
                "강릉", "고성", "동해", "삼척", "속초", "양구", "양양", "영월", "원주", "인제", "정원", "철원", "춘천", "태백", "평창", "홍천", "화천", "횡성"
            }, {
                "가평", "고양", "과천", "광명", "광주", "구리", "군포", "김포", "남양주", "동두천", "부천", "성남", "수원", "시흥", "안산", "안성", "안양", "양주", "양평", "여주", "연천", "오신", "용인", "의왕", "의정부", "이천", "파주", "평택", "포천", "하남", "화성"
            }, {
                "거제", "거창", "고성", "김해", "남해", "밀양", "사천", "산청", "양산", "의령", "진주", "창녕", "창원", "통영", "하동", "함안", "함양", "합천"
            }, {
                "경산", "경주", "고령", "구미", "군위", "김천", "문경", "봉화", "상주", "성주", "안동", "영덕", "영양", "영주", "영천", "예천", "울릉", "울진", "의성", "청도", "청송", "칠곡", "포항"
            }, {
                "강진", "고흥", "곡성", "광양", "구례", "나주", "담양", "목포", "무안", "보성", "순천", "신안", "여수", "영광", "영암", "완도", "장성", "장흥", "진도", "함평", "해남", "화순"
            }, {
                "고창", "군산", "김제", "남원", "무주", "부안", "순창", "완주", "익산", "임실", "장수", "전주", "정읍", "진안"
            }, {
                "계룡", "공주", "금산", "논산", "당진", "보령", "부여", "서산", "서천", "아산", "예산", "천안", "청양", "태안", "홍성"
            }, {
                "괴산", "단양", "보은", "영동", "옥천", "음성", "제천", "증평", "진천", "청주", "충주"
            }, {
                "서귀포", "제주"
            }
    };

}
