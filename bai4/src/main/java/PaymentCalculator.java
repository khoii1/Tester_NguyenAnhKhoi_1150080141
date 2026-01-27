public class PaymentCalculator {
    
    public enum Gender {
        MALE, FEMALE, CHILD
    }
    
    /**
     * Calculate payment based on gender and age
     * 
     * @param gender The gender (MALE, FEMALE, CHILD)
     * @param age The age in years
     * @return Payment amount in euro
     * @throws IllegalArgumentException if age is invalid or gender is null
     */
    public int calculatePayment(Gender gender, int age) {
        // Validate input
        if (gender == null) {
            throw new IllegalArgumentException("Gender cannot be null");
        }
        
        if (age < 0 || age > 145) {
            throw new IllegalArgumentException("Age must be between 0 and 145");
        }
        
        // Calculate payment based on gender and age
        switch (gender) {
            case CHILD:
                if (age >= 0 && age <= 17) {
                    return 50;
                }
                throw new IllegalArgumentException("Child age must be between 0 and 17");
                
            case MALE:
                if (age >= 18 && age <= 35) {
                    return 100;
                } else if (age >= 36 && age <= 50) {
                    return 120;
                } else if (age >= 51 && age <= 145) {
                    return 140;
                }
                throw new IllegalArgumentException("Invalid age for Male");
                
            case FEMALE:
                if (age >= 18 && age <= 35) {
                    return 80;
                } else if (age >= 36 && age <= 50) {
                    return 110;
                } else if (age >= 51 && age <= 145) {
                    return 140;
                }
                throw new IllegalArgumentException("Invalid age for Female");
                
            default:
                throw new IllegalArgumentException("Invalid gender");
        }
    }
    
    /**
     * Alternative method with string parameters for easier use
     */
    public int calculatePayment(String gender, int age) {
        Gender g;
        try {
            g = Gender.valueOf(gender.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid gender: " + gender);
        }
        return calculatePayment(g, age);
    }
}
