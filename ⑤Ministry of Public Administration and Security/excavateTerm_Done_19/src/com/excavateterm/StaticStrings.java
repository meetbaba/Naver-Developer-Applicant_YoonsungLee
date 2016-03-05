/**
 * <pre>
 * 1. ������Ʈ�� : excavateTerm
 * 2. ��Ű����(�Ǵ� ���丮 ���) : com.excavateterm
 * 3. ���ϸ� : StaticStrings.java
 * 4. �ۼ��� : 2015. 8. 8. ���� 4:30:55
 * 5. �ۼ��� : ����
 * 6. ���� :
 * </pre>
 */
package com.excavateterm;

/**
 * ������ : ����
 * �ۼ��� : 2015. 8. 8. ���� 4:30:55
 * ���� : 
 */
public class StaticStrings {

	public static String USER_NUMBER;
	public static String PROJECT_NUMBER;
	public static String PROJECT_STATUS;
	public static String USER_POSITION;
	public static String USER_TEAM;
	
	public static final String POSITION_LEADER="����";
	public static final String POSITION_MEMBER="����";
	
	public static final String EDITTEXT_HINT_MEMBER_RISTRICTION="������ �Է� �Ұ��մϴ�. ��ø� ��ٷ��ּ���.";
	
	public static final String PRO_STATUS_READY="READY";	//�ƹ��͵� �Է¹��� ����
	public static final String PRO_STATUS_ING="ING";		//���� ��� �ܾ� ��������
	public static final String PRO_STATUS_DEFREADY="DEFREADY";		//����ܾ �����ް� ��ü�ܾ� ��������
	public static final String PRO_STATUS_DEFSET="DEFSET";		//��ü�ܾ� ����
	public static final String PRO_STATUS_DEFVOTEREADY="DEFVOTEREADY";		//��ü�ܾ� ��ǥ����
	public static final String PRO_STATUS_DEFVOTE="DEFVOTE";		//��ü�ܾ� ��ǥ
	public static final String PRO_STATUS_END="END";		//��� ������ �Է� ���� �� ����
	
	public static final int WAITTIME=3000;

	public static final String DIALOG_VERSION_CONTENT="�ش� ���� �������� �ʽ��ϴ�. \n-�ȵ���̵� ���� 4.0.3(IceCreamSandwitch MR1) �̻� �����մϴ�.-";
	public static final String DIALOG_VERSION_TITLE="�˼��մϴ�.";
	
	public static final String DIALOG_EXIT_CONTENT="\t\t\t���� �����Ͻðڽ��ϱ�?\n\n�����ư�� ������ ���ݱ��� �۾��� �ڷ��\n\t\t\t������� �ʽ��ϴ�.";
	public static final String DIALOG_EXIT_TITLE="\t����!";
	
	public static final String DIALOG_EXIT_SEVEN_EIGHT_CONTENT="\t\t\t���� �����Ͻðڽ��ϱ�?\n\n�����۾��� ������� �ʰ� ���� �ܰ�� �Ѿ�ϴ�.";
	public static final String DIALOG_EXIT_SEVEN_EIGHT_TITLE="\t����!";
	
	public static final String DIALOG_END_CONTENT="������� ������ �Ϸ�Ǿ����ϴ�.";
	public static final String DIALOG_END_TITLE="�����ϼ̽��ϴ�!";

	public static final String TOAST_WAIT_TERM_TOTAL="��� ���谡 ���� ���۵��� �ʾҽ��ϴ�.";

	public static final String TOAST_TERM_TOTAL_START="��� ���谡 ���۵Ǿ����ϴ�.";
	public static final String TOAST_WRITE_TEAM="�� ��ȣ�� �Է��ϼ���";
	public static final String TOAST_SELECT_POSITION="��å�� �����ϼ���";
	public static final String TOAST_BUTTON_NEXT="�ۼ� ������ Ȯ���Ͻð�, ���� ��ư�� �ٽ� ��������.";
	public static final String TOAST_BUTTON_SEND="�ۼ� ������ Ȯ���Ͻð�, ���� ��ư�� �ٽ� ��������.";
	public static final String TOAST_LIST_UPDATE="����Ʈ�� ���¸� �ҷ����� �� �����߽��ϴ�. �ٽýõ��մϴ�.";
	public static final String TOAST_KEEP_GOING="�̾ ��� �����մϴ�.";
	public static final String TOAST_NEW_START="ó������ �ٽ� �����մϴ�.";
	public static final String TOAST_WAIT_TOTAL="���谡 ���� ������ �ʾҽ��ϴ�. ��ø� ��ٷ��ּ���.";
	public static final String TOAST_WAIT_TOTAL_RESTART="���谡 ���� ������ �ʾҽ��ϴ�. ��ũ�� �����Ȳ�� Ȯ�� ��, �ٽ� �õ����ּ���.";
	public static final String TOAST_WAIT_DEF_TOTAL_START="��ü�ܾ� ������ ���� ���۵��� �ʾҽ��ϴ�. ��ø� ��ٷ��ּ���.";
	public static final String TOAST_DEF_TOTAL_START="��ü�ܾ� ������ ���۵Ǿ����ϴ�.";
	public static final String TOAST_DEF_TOTAL_DONE="�̹� ��ü ����� ����� ��ǥ�� �������ϴ�.";
	public static final String TOAST_WAIT_VOTE="���� ��ǥ�� ���۵��� �ʾҽ��ϴ�.";


	
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

