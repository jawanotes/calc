import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        String paramStr = "";
        for (String arg : args) {
            paramStr += arg;
            //System.out.println(arg);//debug
        }

        
        //paramStr = "V * IX";//debug

        if(0 == paramStr.length()) paramStr = getInputString();

        paramStr = paramStr.trim();
        System.out.println(calc(paramStr));
    }

    public static String calc(String input) throws Exception{
        
        Data inData = new Data(input);
        return inData.getResulString();
    }

/*     static boolean checkParam(String input){
        int mul = "*".codePointAt(0);
        int div = "/".codePointAt(0);
        String  paramArray[] = input.split("[0-9]+[ ]*[+-"+"\\"+mul+"\\"+div+"]+[ ]*[0-9]+", -1);
        if (paramArray.length == 3) return true;
        else return false;

    } */

    private static String getInputString(){
        System.out.println("Usage: java Main <Your expression>\nOr write expression here:");
        Scanner inputScan = new Scanner(System.in);

        String output = inputScan.nextLine();
        inputScan.close();
        return output;
    }

    static class Data{
        char[] operators = new char[]{'+', '-', '*', '/'};
        String[] latins = new String[]{"","I","II","III","IV","V","VI","VII","VIII","IX",
                                       "X","X","XX","XXX","XL","L","LX","LXX","LXXX","XC","C"};
        int x, y, result, opPosition;
        String xStr, yStr;
        char operator = '!';
        boolean isLatin = false;
        

    


        public Data(String inStr) throws Exception {

            x = -999;
            y = x;
            opPosition = x;

            findOperator(inStr);

/*             try {
                /*
                String tmpStr;
                tmpStr = inStr.substring(0, opPosition);
                tmpStr = tmpStr.trim();
                x=Integer.parseInt(tmpStr);
                
                x=Integer.parseInt(inStr.substring(0, opPosition).trim());
                y=Integer.parseInt(inStr.substring(opPosition+1).trim());
            }catch(NumberFormatException e){} */

            checkLatin(inStr);
            xStr = inStr.substring(0, opPosition).trim();
            yStr = inStr.substring(opPosition+1).trim();

            if(isLatin){
                for(int i=1; i<11; ++i){
                    if(xStr.equals(latins[i])) x = i;
                    if(yStr.equals(latins[i])) y = i;
                }

            }else{
                x=Integer.parseInt(xStr);
                y=Integer.parseInt(yStr);
            }

            if(x>10 | x < 0 | y > 10 | y < 0) throwException("Unacceptable argument");
            calculateResult();
            

        }

        private void checkLatin(String input) {
            int fullLen = input.length();
            int sumParts = 0;
            String[] splitted = input.split("[IVX]*");

            
            for (String string : splitted) {
                sumParts += string.length();
            }
            if(sumParts < fullLen) isLatin = true;
            //else isLatin = false;
        }

        private void findOperator(String inStr) throws Exception{
            for(int i=0; i<inStr.length(); i++){
                for(int j=0; j<operators.length; j++){
                    if(inStr.charAt(i) == operators[j]){
                        operator = operators[j];
                        opPosition=i;
                        if( 0==opPosition || inStr.length()-1==opPosition){
                            throwException("Polish notation is not allowed!");
                            
                        }
                        return;
                    }
                }
            }
            throwException("Invalid operator " + inStr);
        }

        private void calculateResult() throws Exception
        {
            switch (operator) {
                case '+':{result = x+y; break;}
                case '-':{result = x-y; break;}
                case '*':{result = x*y; break;}
                case '/':{result = x/y; break;}
                
                default:
                    throwException("Invalid operator");
            }
        }

        private int getResult(){
            return result;
        }

        public String getResulString() throws Exception{
            int result = getResult();
            int x1 = result%10;
            int x10 = result/10;
            
            
            if(isLatin){
                if (result > 0){
                    if(x10 > 0) x10+=10;
                    return latins[x10]+latins[x1];
                }
                throwException("Bad parameters");
            }

            return ""+result;
        }

        private void throwException(String text) throws Exception{
            //System.out.println("Jawa say: UTINNI!");
            throw new Exception(text);
        }
    }


}
