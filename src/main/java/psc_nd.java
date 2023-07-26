import java.util.GregorianCalendar;
import java.util.Calendar;

// 
// Decompiled by Procyon v0.5.36
// 

public class psc_nd
{
    private Calendar a;
    private String b;
    private String c;
    private String d;
    private boolean e;
    public static final String f;
    public static final String g;
    public static final String h;
    public static final String i;
    public static final String j;
    
    public psc_nd() {
        this.c = null;
        this.d = "";
        this.e = false;
    }
    
    protected void a(final Calendar a) {
        this.a = a;
    }
    
    public void a(final String s, final String s2) {
        final GregorianCalendar a = new GregorianCalendar();
        a.set(Integer.parseInt(s.substring(0, 4)), Integer.parseInt(s.substring(4, 6)) - 1, Integer.parseInt(s.substring(6, 8)), Integer.parseInt(s2.substring(0, 2)), Integer.parseInt(s2.substring(2, 4)), 0);
        this.a = a;
    }
    
    public void a(final String b) {
        this.b = b;
    }
    
    public String a() {
        return this.b;
    }
    
    public void a(final String str, final String str2, final String s) {
        this.c = str + " " + str2 + " " + this.b(s);
    }
    
    public String b() {
        return this.c;
    }
    
    public String c() {
        return this.a.getTime().toString();
    }
    
    public void a(final boolean b) {
        if (b) {
            System.out.print(psc_nd.f);
        }
        System.out.println(this.a.getTime());
    }
    
    public void b(final boolean b) {
        if (b) {
            System.out.print(psc_nd.g);
        }
        System.out.println(this.b);
    }
    
    public void c(final boolean b) {
        if (b) {
            System.out.print(psc_nd.h);
        }
        System.out.println(this.c);
    }
    
    public void a(final boolean b, final boolean b2) {
        if (b) {
            System.out.print(psc_nd.i);
        }
        if (b2) {
            System.out.println("Evaluation Build");
        }
        else {
            System.out.println();
        }
    }
    
    public void d(final boolean b) {
        this.a(b, this.g());
        if (this.g()) {
            try {
                this.e();
                this.f();
            }
            catch (Exception ex) {
                if (b) {
                    this.d();
                }
                System.out.println("License is invalid");
            }
        }
    }
    
    public void d() {
        System.out.print(psc_nd.j);
    }
    
    public String e() {
        return this.c;
    }
    
    public String f() {
        return this.d;
    }
    
    private String b(final String s) {
        return s.substring(6, 8) + "-" + this.a(Integer.parseInt(s.substring(4, 6))) + "-" + s.substring(0, 4);
    }
    
    private String a(final int n) {
        switch (n) {
            case 1: {
                return "January";
            }
            case 2: {
                return "February";
            }
            case 3: {
                return "March";
            }
            case 4: {
                return "April";
            }
            case 5: {
                return "May";
            }
            case 6: {
                return "June";
            }
            case 7: {
                return "July";
            }
            case 8: {
                return "August";
            }
            case 9: {
                return "September";
            }
            case 10: {
                return "October";
            }
            case 11: {
                return "November";
            }
            case 12: {
                return "December";
            }
            default: {
                return "Invalid Month";
            }
        }
    }
    
    public boolean g() {
        return this.e;
    }
    
    public void h() {
        this.e = true;
    }
    
    static {
        f = new String("      BUILT_ON: ");
        g = new String("VERSION_NUMBER: ");
        h = new String("       VERSION: ");
        i = new String("          EVAL: ");
        j = new String("       WARNING: ");
    }
}
