import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.max;
import static java.lang.Integer.min;


public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "입력이 잘못되었습니다.";
  
    // implement this
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("");
    private char sign;
    private int[] num = new int[202];
    private int length;
    public BigInteger(int i)
    {
        if(i >= 0){
            this.sign = '+';
        }
        else{
            this.sign = '-';
        }
        if(i ==0){
            this.length = 1;
        }
        else {
            this.length = 0;
            while (i != 0) {
                this.num[this.length] = i % 10;
                i = i / 10;
                this.length++;
            }
        }

    }

    public BigInteger(int[] num1)
    {
        this.sign ='+';
        this.length = num1.length;
        boolean isValid = false;
        for(int i=num1.length-1; i>=0; i--){
            this.num[i] = num1[i];
            if(num1[i]==0&&!isValid&&this.length!=1){
                this.length --;
            }
            else
                isValid = true;
        }
    }

    public BigInteger(String s)
    {
        if(s.charAt(0)<'0'||s.charAt(0)>'9'){
            this.sign = s.charAt(0);
            s= s.substring(1);
        }
        else{
            this.sign = '+';
        }
        for(int i=0; i<s.length(); i++){
            this.num[s.length()-i-1] = s.charAt(i) -'0';
        }
        this.length = s.length();
    }

    public BigInteger add(BigInteger big)
    {
        BigInteger result = new BigInteger(0);
        //부호가 같은 경우 (-a) + (-b) or a + b
        if(this.sign == big.sign){
            result.sign = this.sign;
            result.length = max(this.length, big.length);
            int carry = 0;
            for(int i=0; i<result.length; i++){
                result.num[i] = (this.num[i] + big.num[i] + carry)%10;
                carry = (this.num[i] + big.num[i] + carry)/10;
            }
            if(carry != 0){
                result.length++;
                result.num[result.length-1] = carry;
            }
        }

        //부호가 다른 경우 (-a) +(b) --> b - a , a + (-b) --> a - b
        else if(this.sign =='-'){
            this.sign = '+';
            result = big.subtract(this);
        }
        else{
            big.sign = '+';
            result = subtract(big);
        }

        return result;
    }
  
    public BigInteger subtract(BigInteger big)
    {
        BigInteger result = new BigInteger(0);
        //(-a) - (-b) b-a or (a)-(b) a-b
        if(this.sign == big.sign) {
            if (this.sign == '-') {
                big.sign = '+';
                this.sign = '+';
                result = big.subtract(this);
            }
            else {
                result.length = max(this.length, big.length);

                int carry = 0;
                for (int i = 0; i < result.length; i++) {
                    result.num[i] = (10 + this.num[i] - big.num[i] - carry) % 10;
                    carry = 1 - (10 + this.num[i] - big.num[i] - carry) / 10;
                }
                if (carry == 1){
                    int[] tmp = new int[result.length+1];
                    tmp[result.length] = 1;
                    BigInteger complement = new BigInteger(tmp);
                    result = complement.subtract(result);
                    result.sign = '-';
                    result.length--;
                }
                else{
                    result.sign = '+';
                }
            }
        }
        //(-a) - (b)  --> -(a+b), a.add(b)
        else if(this.sign =='-'){
            this.sign = '+';
            result = this.add(big);
            result.sign = '-';
        }

        //(a) -(-b)  --> a+b, a.add(b)
        else{
            big.sign = '+';
            result = this.add(big);
        }

        return result;
    }

    public BigInteger multiplyNaive(BigInteger big){
        // do the naive multiply algorithm for short numbers.(O(n^2) time complexity)
        BigInteger result = new BigInteger(0);
        result.length = this.length + big.length +1;
        for(int i=0; i<this.length; i++){
            for(int j=0; j<big.length; j++){
                result.num[i+j] += this.num[i]*big.num[j];
                //if there is carry, move carry to the next element.
                if(result.num[i+j]/10!=0){
                    result.num[i+j+1] += result.num[i+j]/10;
                    result.num[i+j] %= 10;
                }
                //else, do nothing but save the value
            }
        }
        return result;
    }

  
    public BigInteger multiply(BigInteger big)
    {
        BigInteger result = new BigInteger(0);

        if(this.length <10 && big.length < 10){
            result = multiplyNaive(big);
        }
        else{
            int halfIndex = max(this.length, big.length)/2;

            BigInteger highOrder1 = new BigInteger(this.num[1]);
            BigInteger lowOrder1 = new BigInteger(this.num[0]);
            BigInteger highOrder2 = new BigInteger(big.num[1]);
            BigInteger lowOrder2 = new BigInteger(big.num[0]);

            if(this.length>2||big.length>2) {
                highOrder1 = new BigInteger(Arrays.copyOfRange(this.num, min(this.length, halfIndex), this.length));
                lowOrder1 = new BigInteger(Arrays.copyOfRange(this.num, 0,  min(this.length, halfIndex)));
                highOrder2 = new BigInteger(Arrays.copyOfRange(big.num, min(big.length, halfIndex), big.length));
                lowOrder2 = new BigInteger(Arrays.copyOfRange(big.num, 0, min(big.length, halfIndex)));
            }


            BigInteger high = highOrder1.multiply(highOrder2);
            BigInteger low = lowOrder1.multiply(lowOrder2);
            BigInteger middle = (highOrder1.add(lowOrder1)).multiply(highOrder2.add(lowOrder2)).subtract(high).subtract(low);

            result = low;
            result.length = 2*halfIndex+high.length;
            int j=0;
            for(int i= 2*halfIndex; i< result.length;i++){
                result.num[i] = high.num[j];
                j++;
            }

            int k=0;
            int[] tmp = new int[halfIndex+middle.length];
            for(int l=halfIndex; l<tmp.length; l++){
                tmp[l] = middle.num[k];
                k++;
            }

            middle = new BigInteger(tmp);
            result = result.add(middle);

        }

        for(int i=result.length-1;i>=0;i--){
            if(result.num[i]==0&&result.length!=1){
                result.length--;
            }
            else{
                break;
            }
        }
        //sign of result
        if(this.sign == big.sign){
            result.sign ='+';
        }
        else{
            result.sign = '-';
        }

        return result;
    }

  
    @Override
    public String toString() {
        String s = "";
        s += (this.sign == '-') ? this.sign : "";
        boolean isVaild = false;
        for(int i= this.length; i>0; i--){
            if(!isVaild&&this.num[i-1]==0){}
            else {
                s+=this.num[i - 1]; isVaild = true;}
        }
        if(s.equals("")){
            s="0";
        }

        return s.toString();
    }
  
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        // implement here
        // parse input
        // using regex is allowed
  
        // One possible implementation
        // BigInteger num1 = new BigInteger(arg1);
        // BigInteger num2 = new BigInteger(arg2);
        // BigInteger result = num1.add(num2);
        // return result;

        input = input.replaceAll("\\p{Z}", "");

        char function = 0;
        String tmp1 = null;
        String tmp2 = null;

        for(int i=0; i<input.length(); i++){
            if(input.charAt(i)>'9'||input.charAt(i)<'0'){
                if(function==0&&i!=0){
                    function = input.charAt(i);
                    tmp1 = input.substring(0, i);
                    tmp2 = input.substring(i+1);
                }
            }
        }
        BigInteger num1 = new BigInteger(tmp1);
        BigInteger num2 = new BigInteger(tmp2);
        BigInteger result = null;

        if(function == '+'){
            result = num1.add(num2);
        }

        else if(function == '-'){
            result = num1.subtract(num2);
        }

        else if(function == '*'){
            result = num1.multiply(num2);
        }
        else{
            result = new BigInteger(-1);
        }


        return result;

    }
  
    public static void main(String[] args) throws Exception
    {
        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
  
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(e);
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
  
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
  
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
  
            return false;
        }
    }
  
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}
