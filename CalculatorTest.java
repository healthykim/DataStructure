import java.io.*;
import java.util.*;


public class CalculatorTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;

				command(input);
			}
			catch (Exception | Error e)
			{
				System.out.println("ERROR");
				//System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	public static void command(String input)
	{
		// TODO : 아래 문장을 삭제하고 구현해라.
		// IDEA ) 파싱하면서 출력한다. 출력하면서 계산한다.

		//parsing : input의 공백을 모두 제거한 뒤 앞에서부터 하나씩 읽는다. 기본적으로 숫자가 나오면 출력(과 동시에 string에 저장)하면서
		//1. '(' : 쌓아
		//2. ')' : '('만날 때 까지 빼
		//3. '^' : 쌓아
		//4. '-' : 쌓아('~'로 바꾸기)
		//5. '*, /, %' : 지랑 동등한 애를 다 빼고 쌓아
		//6. '+,-' : '('만날때까지 다 빼고 쌓아

		//여기서 뺄 게 없으면 invalid equation -> 언더플로우 캐치, out/err "ERROR"

		input = input.replaceAll("\\s", " ");
		StringBuilder postFix = new StringBuilder();
		long result =0;
		Stack<Long> numStack = new Stack<>();
		Stack<Character> opStack = new Stack<>();
		boolean wasSpace = true;
		boolean wasNumber = false;
		for(int i=0; i<input.length(); i++){
			if(9>=(input.charAt(i)-'0')&&(input.charAt(i)-'0')>=0){
				if(!wasNumber&&postFix.length()>0&&postFix.charAt(postFix.length()-1)!=' ') {
					postFix.append(" ");
				}
				postFix.append(input.charAt(i));
				wasNumber = true;
				wasSpace = false;
			}

			//1. '(' : 쌓아
			else if(input.charAt(i)=='('){
				if(wasNumber==true){
					throw new Error("ERROR");
				}
				else {
					opStack.push(input.charAt(i));
					//postFix.append(" ");	//(는 어차피 숫자 뒤에 안오고 연산자 뒤에 오는데 연산자가 이미 공백 띄워둠
				}
				wasSpace = false;

			}

			//2. ')' : '('만날 때 까지 빼
			else if(input.charAt(i)==')'){
				if(wasNumber==false){
					throw new Error("ERROR");
				}
				while(opStack.peek() != '('){
					if(postFix.length()>0&&postFix.charAt(postFix.length()-1)!=' ') {
						postFix.append(" ");
					}
					postFix.append(opStack.pop());
				}
				opStack.pop();	//(를 만났으니까 빼버림
				wasSpace = false;

			}

			//3. '^' : 쌓아
			else if(input.charAt(i)=='^'){
				opStack.push(input.charAt(i));
				if(postFix.length()>0&&postFix.charAt(postFix.length()-1)!=' ') {
					postFix.append(" ");
				}
				wasSpace = false;
				wasNumber = false;

			}

			//4. '-' : 쌓아('~'로 바꾸기) //구분 어떻게 함?
			else if(input.charAt(i)=='-'&&!wasNumber){
				opStack.push('~');
				//postFix.append(" ");
				wasSpace = false;
				wasNumber = false;
			}

			//5. '*, /, %' : 지랑 동등한 애를 다 빼고 쌓아
			else if(input.charAt(i)=='*'||input.charAt(i)=='/'||input.charAt(i)=='%'){
				while(!opStack.empty()&&(opStack.peek().equals('*')||opStack.peek().equals('/')||opStack.peek().equals('%')
						||opStack.peek().equals('^')||opStack.peek().equals('~'))){
					if(postFix.length()>0&&postFix.charAt(postFix.length()-1)!=' ') {
						postFix.append(" ");
					}
					postFix.append(opStack.pop());
				}
				if(postFix.length()>0&&postFix.charAt(postFix.length()-1)!=' ') {
					postFix.append(" ");
				}
				opStack.push(input.charAt(i));
				wasSpace = false;
				wasNumber = false;

			}

			//6. '+,-' : '('만날때까지 다 빼고 쌓아
			else if(input.charAt(i)=='+'||input.charAt(i)=='-'){
				while(!opStack.empty()&&(!opStack.peek().equals('('))){
					if(postFix.length()>0&&postFix.charAt(postFix.length()-1)!=' ') {
						postFix.append(" ");
					}
					postFix.append(opStack.pop());
				}
				if(postFix.length()>0&&postFix.charAt(postFix.length()-1)!=' ') {
					postFix.append(" ");
				}
				wasSpace = false;
				wasNumber = false;
				//System.out.println(input.charAt(i));
				opStack.push(input.charAt(i));
			}
			else if(input.charAt(i)==' '){
				//System.out.println(postFix.charAt(postFix.length()-1));
				if(!wasSpace&&postFix.length()>0&&postFix.charAt(postFix.length()-1)!=' '){
					postFix.append(" ");
				}
				wasSpace = true;
			}

			else{
				throw new Error("ERROR");
			}
			//System.out.println(postFix.toString());
		}

		while(!opStack.isEmpty()){
			if(!wasSpace&&postFix.length()>0&&postFix.charAt(postFix.length()-1)!=' '){
				postFix.append(" ");

			}
			//System.out.println(opStack.peek());
			wasSpace = false;
			postFix.append(opStack.pop());
		}
		//System.out.println(postFix.toString());

		//계산 : 숫자면 스택에 넣고 숫자 아니면 숫자 빼서 계산하고 다시 넣음. 마지막에 스택에 남은 게 하나여야 하고 그게 답임 하나 이상 있으면 에러
		//postFixAll : postFix 변환결과를 배열로 변환한 것
		String[] postFixAll = postFix.toString().trim().split(" ");

		for(int i=0;i<postFixAll.length;i++){
			if(postFixAll[i].equals("~")){
				if(numStack.isEmpty()){
					throw new Error("ERROR");
				}
				else {
					result = -numStack.pop();
					numStack.push(result);
				}
			}
			else if(postFixAll[i].equals("^")){
				long a = numStack.pop();
				long b = numStack.pop();
				if(b==0&&0>a){
					throw new Error("ERROR");
				}
				else {
					result = (long) Math.pow(b, a);
					numStack.push(result);
				}
			}
			else if(postFixAll[i].equals("*")){
				result = numStack.pop() * numStack.pop();
				//System.err.println(result);

				numStack.push(result);
			}
			else if(postFixAll[i].equals("/")){
				long a = numStack.pop();
				long b = numStack.pop();
				if(a==0) {
					throw new Error("ERROR");
				}
				else {
					result = b / a;
					numStack.push(result);
				}
			}
			else if(postFixAll[i].equals("%")){
				long a = numStack.pop();
				long b = numStack.pop();
				if(a==0){
					throw new Error("ERROR");
				}
				else {
					result = b % a;
					numStack.push(result);
				}
			}
			else if(postFixAll[i].equals("+")){
				result = numStack.pop()+numStack.pop();
				numStack.push(result);
			}
			else if(postFixAll[i].equals("-")){
				long a = numStack.pop();
				long b = numStack.pop();
				result = b-a;
				numStack.push(result);
			}
			else {
				//숫자다
				numStack.push(Long.parseLong(postFixAll[i]));
			}

		}

		result = numStack.pop();
		if(numStack.empty()){
			System.out.println(postFix.toString().trim());
			System.out.println(result);
		}
		else{
			throw new Error("ERROR");
		}

	}
}
