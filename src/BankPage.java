import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class BankPage extends JFrame {

    private JLabel firstName, lastName, chequing, savings, investment, arrow;
    private JTextField tfFirstName, tfLastName, tfChequing, tfSavings, tfInvestment, tfElseTransferAmt, tfDeposit, tfBill, tfSelfTransferAmt;
    private JButton btnAdd, btnDelete, btnElseTransfer, btnDeposit, btnBill, btnSelfTransfer, btnMonth;
    private List accountNames;
    private ArrayList<CADAccount> accountList = new ArrayList<>();
    private JComboBox jcbFromName, jcbToName, jcbFromType, jcbToType;

    private int selected, fromUser, toUser, fromType, toType;

    public BankPage() {
        Frame frame = new JFrame("Bank Account Management");
        frame.setLocation(10, 10);
        frame.setSize(1200, 800);
        frame.setLayout(new BorderLayout());
        Panel centerPane = new Panel();
        frame.add(centerPane, BorderLayout.CENTER);
        centerPane.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
//-------------------------------------------------------------------------------------------------------------
        accountNames = new List();
            accountNames.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    selected = accountNames.getSelectedIndex();
                    refreshInfo();
                }
            });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 3;
        gbc.weightx = 0.7;
        gbc.weighty = 0.5;
        centerPane.add(accountNames, gbc);
//-------------------------------------------------------------------------------------------------------------
        JPanel pInput = new JPanel();  //   centerPane.add(pInput, gbc);   Panel(centerPane) can hold children panel( pInput)
        pInput.setLayout(new GridLayout(6, 2));

        firstName = new JLabel("First Name");
        tfFirstName = new JTextField(10);

        lastName = new JLabel("Last Name");
        tfLastName = new JTextField(10);

        chequing = new JLabel("Chequing");
        tfChequing = new JTextField(10);

        savings = new JLabel("Savings");
        tfSavings = new JTextField(10);

        investment = new JLabel("Investment");
        tfInvestment = new JTextField(10);

        jcbToName = new JComboBox();
        jcbFromName = new JComboBox();

        btnAdd = new JButton("add");
            btnAdd.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    String fN = tfFirstName.getText();
                    String lN = tfLastName.getText();
                    String che = tfChequing.getText();
                    String sav = tfSavings.getText();
                    String inv = tfInvestment.getText();
                    if (!fN.equals("") && !lN.equals("") && !che.equals("") && !sav.equals("") && !inv.equals("")){
                        CADAccount tmp = new CADAccount(fN, lN, che, sav, inv);
                        accountList.add(tmp);
                        accountNames.add(fN + " " + lN);

                        jcbFromName.addItem(fN + " " + lN);
                        jcbToName.addItem(fN + " " + lN);
                    }
                    else popup("empty fields");
                }
            } );
        btnDelete = new JButton("delete");
            btnDelete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    accountList.remove(selected);
                    accountNames.remove(selected);

                    jcbFromName.removeItemAt(selected);
                    jcbToName.removeItemAt(selected);
                }
            });

        pInput.add(firstName);
        pInput.add(tfFirstName);
        pInput.add(lastName);
        pInput.add(tfLastName);
        pInput.add(chequing);
        pInput.add(tfChequing);
        pInput.add(savings);
        pInput.add(tfSavings);
        pInput.add(investment);
        pInput.add(tfInvestment);
        pInput.add(btnAdd);
        pInput.add(btnDelete);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 3;
        gbc.weightx = 0.3;
        gbc.weighty = 0.5;
        centerPane.add(pInput, gbc);
//-------------------------------------------------------------------------------------------------------------
        JPanel elseTransfer = new JPanel();
        elseTransfer.setLayout(new GridLayout(2, 2));
            jcbFromName.addItemListener(e -> fromUser = jcbFromName.getSelectedIndex());
            jcbToName.addItemListener(e -> toUser = jcbToName.getSelectedIndex());
        tfElseTransferAmt = new JTextField();
        btnElseTransfer = new JButton("Transfer");
            btnElseTransfer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!tfElseTransferAmt.getText().equals("")){
                        double amt = Double.valueOf(tfElseTransferAmt.getText());
                        if (accountList.get(fromUser).getChequing() >= amt) {
                            accountList.get(toUser).changeChequing(amt);
                            accountList.get(fromUser).changeChequing(-amt);
                            refreshInfo();
                        }
                        else popup("insufficient funds");
                    }
                    else popup("empty fields");
                }
            });

        elseTransfer.add(jcbFromName);
        elseTransfer.add(jcbToName);
        elseTransfer.add(tfElseTransferAmt);
        elseTransfer.add(btnElseTransfer);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 0.35;
        centerPane.add(elseTransfer, gbc);
//-------------------------------------------------------------------------------------------------------------
        JPanel manageBalance = new JPanel();
        manageBalance.setLayout(new GridLayout(2, 2));
        btnDeposit = new JButton("Deposit");
        tfDeposit = new JTextField();
            btnDeposit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!tfDeposit.getText().equals("")) {
                        accountList.get(selected).changeChequing(Double.valueOf(tfDeposit.getText()));
                        refreshInfo();
                    }
                    else popup("empty field");
                }
            });
        btnBill = new JButton("Bill");
        tfBill = new JTextField();
            btnBill.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!tfBill.getText().equals("")){
                        double amt = Double.valueOf(tfBill.getText());
                        accountList.get(selected).changeChequing(-amt);
                        refreshInfo();
                        if (accountList.get(selected).getChequing()<amt)
                            popup("user has no money left");
                    }
                    else popup("empty field");
                }
            });

        manageBalance.add(btnDeposit);
        manageBalance.add(tfDeposit);
        manageBalance.add(btnBill);
        manageBalance.add(tfBill);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.weightx = 0.5;
        gbc.weighty = 0.35;
        centerPane.add(manageBalance, gbc);
//-------------------------------------------------------------------------------------------------------------
        JPanel selfTransfer = new JPanel();
        selfTransfer.setLayout(new GridLayout(1,6));
        tfSelfTransferAmt = new JTextField();
        String[] accountTypes = new String[] {"Chequing", "Saving", "Investment"};
        jcbFromType = new JComboBox(accountTypes);
            jcbFromType.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    fromType = jcbFromType.getSelectedIndex();
                }
            });
        arrow = new JLabel("---->", SwingConstants.CENTER);
        jcbToType = new JComboBox(accountTypes);
            jcbToType.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    toType = jcbToType.getSelectedIndex();
                }
            });
        btnSelfTransfer = new JButton("Transfer");
            btnSelfTransfer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    boolean approved = false;
                    if (fromType!=-1 && toType!=-1 && !tfSelfTransferAmt.getText().equals("")){
                        double amt = Double.valueOf(tfSelfTransferAmt.getText());
                        if ( (fromType ==0 && accountList.get(selected).getChequing()>=amt)||
                             (fromType ==1 && accountList.get(selected).getSaving()>=amt)||
                             (fromType ==2 && accountList.get(selected).getInvest()>=amt))
                                approved = true;

                        if (approved) {
                            if (fromType == 0) accountList.get(selected).changeChequing(-amt);
                            if (fromType == 1) accountList.get(selected).changeSaving(-amt);
                            if (fromType == 2) accountList.get(selected).changeInvest(-amt);
                            if (toType == 0) accountList.get(selected).changeChequing(amt);
                            if (toType == 1) accountList.get(selected).changeSaving(amt);
                            if (toType == 2) accountList.get(selected).changeInvest(amt);
                        }
                        else popup("insufficient funds");
                        refreshInfo();
                    }
                }
            });
        btnMonth = new JButton("+Month");
            btnMonth.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    accountList.get(selected).plusMonth();
                    refreshInfo();
                }
            });

        selfTransfer.add(tfSelfTransferAmt);
        selfTransfer.add(jcbFromType);
        selfTransfer.add(arrow);
        selfTransfer.add(jcbToType);
        selfTransfer.add(btnSelfTransfer);
        selfTransfer.add(btnMonth);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.weightx = 1;
        gbc.weighty = 0.15;
        centerPane.add(selfTransfer, gbc);


        frame.setVisible(true);
    }

    public void refreshInfo(){
        tfFirstName.setText(accountList.get(selected).getFirstName());
        tfLastName.setText(accountList.get(selected).getLastName());
        tfChequing.setText(accountList.get(selected).fChequing());
        tfSavings.setText(accountList.get(selected).fSaving());
        tfInvestment.setText(accountList.get(selected).fInvest());
    }
    public void popup(String mssge){
        JOptionPane.showMessageDialog(null, mssge);
    }

}
