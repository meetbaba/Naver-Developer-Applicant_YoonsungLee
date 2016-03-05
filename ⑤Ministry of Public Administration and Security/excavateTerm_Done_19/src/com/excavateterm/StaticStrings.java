/**
 * <pre>
 * 1. 프로젝트명 : excavateTerm
 * 2. 패키지명(또는 디렉토리 경로) : com.excavateterm
 * 3. 파일명 : StaticStrings.java
 * 4. 작성일 : 2015. 8. 8. 오후 4:30:55
 * 5. 작성자 : 윤성
 * 6. 설명 :
 * </pre>
 */
package com.excavateterm;

/**
 * 개발자 : 윤성
 * 작성일 : 2015. 8. 8. 오후 4:30:55
 * 설명 : 
 */
public class StaticStrings {

	public static String USER_NUMBER;
	public static String PROJECT_NUMBER;
	public static String PROJECT_STATUS;
	public static String USER_POSITION;
	public static String USER_TEAM;
	
	public static final String POSITION_LEADER="팀장";
	public static final String POSITION_MEMBER="팀원";
	
	public static final String EDITTEXT_HINT_MEMBER_RISTRICTION="팀원은 입력 불가합니다. 잠시만 기다려주세요.";
	
	public static final String PRO_STATUS_READY="READY";	//아무것도 입력받지 않음
	public static final String PRO_STATUS_ING="ING";		//현장 사용 단어 모집상태
	public static final String PRO_STATUS_DEFREADY="DEFREADY";		//현장단어를 모집받고 대체단어 수집직전
	public static final String PRO_STATUS_DEFSET="DEFSET";		//대체단어 수집
	public static final String PRO_STATUS_DEFVOTEREADY="DEFVOTEREADY";		//대체단어 투표직전
	public static final String PRO_STATUS_DEFVOTE="DEFVOTE";		//대체단어 투표
	public static final String PRO_STATUS_END="END";		//모든 데이터 입력 종료 끝 상태
	
	public static final int WAITTIME=3000;

	public static final String DIALOG_VERSION_CONTENT="해당 기기는 지원하지 않습니다. \n-안드로이드 버전 4.0.3(IceCreamSandwitch MR1) 이상 지원합니다.-";
	public static final String DIALOG_VERSION_TITLE="죄송합니다.";
	
	public static final String DIALOG_EXIT_CONTENT="\t\t\t정말 종료하시겠습니까?\n\n종료버튼을 누르면 지금까지 작업한 자료는\n\t\t\t저장되지 않습니다.";
	public static final String DIALOG_EXIT_TITLE="\t주의!";
	
	public static final String DIALOG_EXIT_SEVEN_EIGHT_CONTENT="\t\t\t정말 종료하시겠습니까?\n\n현재작업이 저장되지 않고 이전 단계로 넘어갑니다.";
	public static final String DIALOG_EXIT_SEVEN_EIGHT_TITLE="\t주의!";
	
	public static final String DIALOG_END_CONTENT="행정용어 개선이 완료되었습니다.";
	public static final String DIALOG_END_TITLE="수고하셨습니다!";

	public static final String TOAST_WAIT_TERM_TOTAL="용어 집계가 아직 시작되지 않았습니다.";

	public static final String TOAST_TERM_TOTAL_START="용어 집계가 시작되었습니다.";
	public static final String TOAST_WRITE_TEAM="팀 번호를 입력하세요";
	public static final String TOAST_SELECT_POSITION="직책을 선택하세요";
	public static final String TOAST_BUTTON_NEXT="작성 내용을 확인하시고, 다음 버튼을 다시 누르세요.";
	public static final String TOAST_BUTTON_SEND="작성 내용을 확인하시고, 전송 버튼을 다시 누르세요.";
	public static final String TOAST_LIST_UPDATE="리스트의 상태를 불러오는 데 실패했습니다. 다시시도합니다.";
	public static final String TOAST_KEEP_GOING="이어서 계속 진행합니다.";
	public static final String TOAST_NEW_START="처음부터 다시 시작합니다.";
	public static final String TOAST_WAIT_TOTAL="집계가 아직 끝나지 않았습니다. 잠시만 기다려주세요.";
	public static final String TOAST_WAIT_TOTAL_RESTART="집계가 아직 끝나지 않았습니다. 워크샵 진행상황을 확인 후, 다시 시도해주세요.";
	public static final String TOAST_WAIT_DEF_TOTAL_START="대체단어 수집이 아직 시작되지 않았습니다. 잠시만 기다려주세요.";
	public static final String TOAST_DEF_TOTAL_START="대체단어 수집이 시작되었습니다.";
	public static final String TOAST_DEF_TOTAL_DONE="이미 대체 용어의 집계와 투표가 끝났습니다.";
	public static final String TOAST_WAIT_VOTE="아직 투표가 시작되지 않았습니다.";


	
	public static final int STATUS_PRE=1;
	public static final int STATUS_CHOICE=2;
	public static final int STATUS_COMPLETE=3;
	
	public static final int FLAG_SIX_CLICK=1;
	public static final int FLAG_EIGHT_CLICK=2;

	
	public static final int REQUEST_CODE_SIX_VOTE=1;
	public static final int RESULT_CODE_SIX_VOTE=1;
	public static final int REQUEST_CODE_SEVEN_VOTE=2;
	public static final int RESULT_CODE_SEVEN_VOTE=2;
	
	public static final int REQUEST_CODE_TRANSPARENT=3;
	public static final int RESULT_CODE_TRANSPARENT=3;
	
	public static final int KEEP_GOING_VALUE=1;
	public static final int NEW_START_VALUE=2;


	public static final String EXTRA_TRANSPARENT_VALUE="Transparent Value";
	public static final String EXTRA_POSITION_VALUE="Position Value";
	public static final String EXTRA_WORD_ARRAY="Word Array";

	public static final String URL="http://hptest.hplove.kr/WS/ajax.php";
	
	
	public static Boolean EXIT_FLAG=true;
	public static int CURRENT_ACTIVITY;
	public static final int FIRST_ACTIVITY=1;
	public static final int SECOND_ACTIVITY=2;
	public static final int THIRD_ACTIVITY=3;
	public static final int FOURTH_ACTIVITY=4;
	public static final int FIFTH_ACTIVITY=5;
	public static final int SIXTH_ACTIVITY=6;
	public static final int SEVENTH_ACTIVITY=7;
	public static final int EIGHTH_ACTIVITY=8;

	public static final int HANDLER_CHECK_STATUS=51;
	public static final int HANDLER_GO_NEXT=60;
	
	
	//using sixth Activity
	public static final int HANDLER_NEXT_STATUS=22;
	public static final int HANDLER_CHECK_NEXT_FLAG=23;
	public static final int HANDLER_MISMATCH_ITEM=24;
	
	public static final int HANDLER_DEF_WRITE_TRUE=31;
	public static final int HANDLER_DEF_VOTE=41;
	
	public static final int HANDLER_POST_FAIL=100;
	public static final int HANDLER_UPDATE_WORDS_STATUS=97;

	
}

