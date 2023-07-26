// 
// Decompiled by Procyon v0.5.36
// 

package HTTPClient;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Panel;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.event.WindowListener;
import java.awt.TextField;
import java.awt.Label;
import java.awt.Dimension;
import java.awt.Frame;

class SimpleAuthPopup implements AuthorizationPrompter
{
    private static BasicAuthBox inp;
    
    public NVPair getUsernamePassword(final AuthorizationInfo authorizationInfo, final boolean b) {
        String string;
        String s;
        String string2;
        if (authorizationInfo.getScheme().equalsIgnoreCase("SOCKS5")) {
            string = "Enter username and password for SOCKS server on host";
            s = authorizationInfo.getHost();
            string2 = "Authentication Method: username/password";
        }
        else {
            string = "Enter username and password for realm `" + authorizationInfo.getRealm() + "'";
            s = "on host " + authorizationInfo.getHost() + ":" + authorizationInfo.getPort();
            string2 = "Authentication Scheme: " + authorizationInfo.getScheme();
        }
        synchronized (this.getClass()) {
            if (SimpleAuthPopup.inp == null) {
                SimpleAuthPopup.inp = new BasicAuthBox();
            }
        }
        return SimpleAuthPopup.inp.getInput(string, s, string2, authorizationInfo.getScheme());
    }
    
    static {
        SimpleAuthPopup.inp = null;
    }
    
    private static class BasicAuthBox extends Frame
    {
        private static final String title = "Authorization Request";
        private Dimension screen;
        private Label line1;
        private Label line2;
        private Label line3;
        private TextField user;
        private TextField pass;
        private int done;
        private static final int OK = 1;
        private static final int CANCEL = 0;
        
        BasicAuthBox() {
            super("Authorization Request");
            this.screen = this.getToolkit().getScreenSize();
            this.addNotify();
            this.addWindowListener(new Close());
            this.setLayout(new BorderLayout());
            final Panel comp = new Panel(new GridLayout(3, 1));
            comp.add(this.line1 = new Label());
            comp.add(this.line2 = new Label());
            comp.add(this.line3 = new Label());
            this.add("North", comp);
            final Panel comp2 = new Panel(new GridLayout(2, 1));
            comp2.add(new Label("Username:"));
            comp2.add(new Label("Password:"));
            this.add("West", comp2);
            final Panel comp3 = new Panel(new GridLayout(2, 1));
            comp3.add(this.user = new TextField(30));
            comp3.add(this.pass = new TextField(30));
            this.pass.addActionListener(new Ok());
            this.pass.setEchoChar('*');
            this.add("East", comp3);
            final GridBagLayout layout = new GridBagLayout();
            final Panel comp4 = new Panel(layout);
            final GridBagConstraints gridBagConstraints = new GridBagConstraints();
            final Panel panel = new Panel();
            comp4.add(panel);
            gridBagConstraints.gridwidth = 0;
            layout.setConstraints(panel, gridBagConstraints);
            gridBagConstraints.gridwidth = 1;
            gridBagConstraints.weightx = 1.0;
            final Button comp5;
            comp4.add(comp5 = new Button("  OK  "));
            comp5.addActionListener(new Ok());
            gridBagConstraints.weightx = 1.0;
            layout.setConstraints(comp5, gridBagConstraints);
            final Button comp6;
            comp4.add(comp6 = new Button("Clear"));
            comp6.addActionListener(new Clear());
            gridBagConstraints.weightx = 2.0;
            layout.setConstraints(comp6, gridBagConstraints);
            final Button comp7;
            comp4.add(comp7 = new Button("Cancel"));
            comp7.addActionListener(new Cancel());
            gridBagConstraints.weightx = 1.0;
            layout.setConstraints(comp7, gridBagConstraints);
            this.add("South", comp4);
            this.pack();
        }
        
        synchronized NVPair getInput(final String text, final String text2, final String text3, final String s) {
            this.line1.setText(text);
            this.line2.setText(text2);
            this.line3.setText(text3);
            this.line1.invalidate();
            this.line2.invalidate();
            this.line3.invalidate();
            this.setResizable(true);
            this.pack();
            this.setResizable(false);
            this.setLocation((this.screen.width - this.getPreferredSize().width) / 2, (int)((this.screen.height - this.getPreferredSize().height) / 2 * 0.7));
            boolean b = true;
            if (s.equalsIgnoreCase("NTLM")) {
                try {
                    this.user.setText(System.getProperty("user.name", ""));
                    b = false;
                }
                catch (SecurityException ex) {}
            }
            this.setVisible(true);
            if (b) {
                this.user.requestFocus();
            }
            else {
                this.pass.requestFocus();
            }
            try {
                this.wait();
            }
            catch (InterruptedException ex2) {}
            this.setVisible(false);
            final NVPair nvPair = new NVPair(this.user.getText(), this.pass.getText());
            this.user.setText("");
            this.pass.setText("");
            if (this.done == 0) {
                return null;
            }
            return nvPair;
        }
        
        private class Ok implements ActionListener
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                BasicAuthBox.this.done = 1;
                synchronized (BasicAuthBox.this) {
                    BasicAuthBox.this.notifyAll();
                }
            }
        }
        
        private class Clear implements ActionListener
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                BasicAuthBox.this.user.setText("");
                BasicAuthBox.this.pass.setText("");
                BasicAuthBox.this.user.requestFocus();
            }
        }
        
        private class Cancel implements ActionListener
        {
            public void actionPerformed(final ActionEvent actionEvent) {
                BasicAuthBox.this.done = 0;
                synchronized (BasicAuthBox.this) {
                    BasicAuthBox.this.notifyAll();
                }
            }
        }
        
        private class Close extends WindowAdapter
        {
            public void windowClosing(final WindowEvent windowEvent) {
                new Cancel().actionPerformed(null);
            }
        }
    }
}
