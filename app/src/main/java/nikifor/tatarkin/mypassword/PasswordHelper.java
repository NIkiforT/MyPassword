package nikifor.tatarkin.mypassword;

public class PasswordHelper {
    private String[] mRussians;
    private String[] mLatins;




    public PasswordHelper(String[] russians, String[] latins){
        if(russians.length != latins.length){
            throw new IllegalArgumentException();
        }

        mRussians = russians;
        mLatins = latins;
    }

    public String convert(CharSequence source) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);

            if (c == 'х'){
                result.append('[');
                continue;
            }else if (c == 'Х'){
                result.append('{');
                continue;
            }else if (c == 'ж'){
                result.append(';');
                continue;
            }else if (c == 'Ж'){
                result.append(':');
                continue;
            }else if (c == 'э'){
                result.append('\'');
                continue;
            }else if (c == 'Э'){
                result.append('\"');
                continue;
            }else if (c == 'б'){
                result.append(',');
                continue;
            }else if (c == 'Б'){
                result.append('<');
                continue;
            }else if (c == 'ю'){
                result.append('.');
                continue;
            }else if (c == 'Ю'){
                result.append('>');
                continue;
            }

            String key = String.valueOf(Character.toLowerCase(c));

            for (int j = 0; j < mRussians.length; j++) {
                if (key.equals(mRussians[j])) {
                    result.append(Character.isUpperCase(c) ?
                            mLatins[j].toUpperCase() : mLatins[j]);
                }
            }

            if (result.length() <= i) {
                result.append(c);
            }
        }
            return result.toString();
        }


    public int getQuality (CharSequence password){
        return Math.min(password.length(), 10);
    }
}

