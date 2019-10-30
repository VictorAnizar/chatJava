
import java.net.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;


public class client_frame extends javax.swing.JFrame 
{
        private ObjectOutputStream salida;

    String username, address = "localhost";
    ArrayList<String> users = new ArrayList();
    int port = 2222;
    Boolean isConnected = false; //para saber si está conectado se usa una bandera
    
    Socket sock; 
    BufferedReader reader;
    PrintWriter writer;
    
    //-------------Para iniciar un hilo-------------//
    
    public void ListenThread() 
    {
         Thread IncomingReader = new Thread(new IncomingReader());
         IncomingReader.start();
    }
    
    //-------------Para agregar un dato al arrayList de usuarios-------------//
    
    public void userAdd(String data) 
    {
         users.add(data);
    }
    
    //-------------Para indicar que un usuario se agrega al dinal de ta_chat-------------//
    
    public void userRemove(String data) 
    {   
         ta_chat.append(data + " Ahora está desconectado.\n");
    }
    
    //--------------------------//
    
    public void writeUsers() 
    {
         String[] tempList = new String[(users.size())];
         users.toArray(tempList);
         for (String token:tempList) 
         {
             //users.append(token + "\n");
         }
    }
    
    //------------Para indicar que un usuario se desconecta--------------//
    
    public void sendDisconnect() 
    {
        String bye = (username + ": :Desconectado");
        try
        {
            writer.println(bye); 
            writer.flush(); 
        } catch (Exception e) 
        {
            ta_chat.append("No se puede mostrar mensaje de desconectar.\n");
        }
    }

    //-----------Para indicarle a la pantalla que se desconecta un usuario---------------//
    
    public void Disconnect() 
    {
        try 
        {
            ta_chat.append("Desconectado.\n");
            sock.close();
        } catch(Exception ex) {
            ta_chat.append("Fallo al desconectar. \n");
        }
        isConnected = false;
        tf_usuario.setEditable(true);

    }
    //Para que inicien todos los elementos de la interfaz gráfica
    public client_frame() 
    {
        initComponents();
    }
    
    //--------------------------//
    
    public class IncomingReader implements Runnable
    {
        @Override
        public void run() 
        {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try 
            {
                while ((stream = reader.readLine()) != null) 
                {
                     data = stream.split(":");

                     if (data[2].equals(chat)) 
                     {
                        ta_chat.append(data[0] + ": " + data[1] + "\n");
                        ta_chat.setCaretPosition(ta_chat.getDocument().getLength());        
                     } 
                     else if (data[2].equals(connect))
                     {
                        ta_chat.removeAll();
                        userAdd(data[0]);
                     } 
                     else if (data[2].equals(disconnect)) 
                     {
                         userRemove(data[0]);
                     } 
                     else if (data[2].equals(done)) 
                     {
                        //users.setText("");
                        writeUsers();
                        users.clear();
                     }
                }
           }catch(Exception ex) 
           {
               ta_chat.append("Error al obtener informacion del usuario.\n");
           }
        }
    }

    //--------------------------//
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        lb_direccion = new javax.swing.JLabel();
        tf_direccion = new javax.swing.JTextField();
        lb_puerto = new javax.swing.JLabel();
        tf_puerto = new javax.swing.JTextField();
        lb_usuario = new javax.swing.JLabel();
        tf_usuario = new javax.swing.JTextField();
        lb_contrasenia = new javax.swing.JLabel();
        tf_contrasenia = new javax.swing.JTextField();
        b_conectar = new javax.swing.JButton();
        b_desconectar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        tf_chat = new javax.swing.JTextField();
        b_enviar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - VentanaCliente");
        setName("client"); // NOI18N
        setResizable(false);

        lb_direccion.setText("Dirección:");

        tf_direccion.setText("localhost");
        tf_direccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_direccionActionPerformed(evt);
            }
        });

        lb_puerto.setText("Puerto:");

        tf_puerto.setText("2222");
        tf_puerto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_puertoActionPerformed(evt);
            }
        });

        lb_usuario.setText("Usuario:");

        tf_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_usuarioActionPerformed(evt);
            }
        });

        lb_contrasenia.setText("Contraseña:");

        b_conectar.setText("Conectar");
        b_conectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_conectarActionPerformed(evt);
            }
        });

        b_desconectar.setText("Desconectar");
        b_desconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_desconectarActionPerformed(evt);
            }
        });

        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        b_enviar.setText("Enviar");
        b_enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_enviarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tf_chat, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b_enviar, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lb_usuario, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                            .addComponent(lb_direccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tf_direccion, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                            .addComponent(tf_usuario))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lb_contrasenia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lb_puerto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tf_contrasenia)
                            .addComponent(tf_puerto, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(b_conectar)
                        .addGap(2, 2, 2)
                        .addComponent(b_desconectar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_direccion)
                    .addComponent(tf_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lb_puerto)
                    .addComponent(tf_puerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tf_usuario)
                    .addComponent(tf_contrasenia)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_usuario)
                        .addComponent(lb_contrasenia)
                        .addComponent(b_conectar)
                        .addComponent(b_desconectar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_chat)
                    .addComponent(b_enviar, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>                        

    private void tf_direccionActionPerformed(java.awt.event.ActionEvent evt) {                                             
       
    }                                            

    private void tf_puertoActionPerformed(java.awt.event.ActionEvent evt) {                                          
   
    }                                         

    private void tf_usuarioActionPerformed(java.awt.event.ActionEvent evt) {                                           
    
    }                                          

    private void b_conectarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        if (isConnected == false) 
        {
            username = tf_usuario.getText();
            tf_usuario.setEditable(false);

            try 
            {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect");
                writer.flush(); 
                isConnected = true; 
            } 
            catch (Exception ex) 
            {
                ta_chat.append("Cannot Connect! Try Again. \n");
                tf_usuario.setEditable(true);
            }
            
            ListenThread();
            
        } else if (isConnected == true) 
        {
            ta_chat.append("You are already connected. \n");
        }
    }                                          

    private void b_desconectarActionPerformed(java.awt.event.ActionEvent evt) {                                              
        sendDisconnect();
        Disconnect();
    }                                             

    private void b_enviarActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String nothing = "";
        if ((tf_chat.getText()).equals(nothing)) {
            tf_chat.setText("");
            tf_chat.requestFocus();
        } else {
            try {
               writer.println(username + ":" + tf_chat.getText() + ":" + "Chat");
               writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                ta_chat.append("Message was not sent. \n");
            }
            tf_chat.setText("");
            tf_chat.requestFocus();
        }

        tf_chat.setText("");
        tf_chat.requestFocus();
    }                                        

    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
            {
                new client_frame().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton b_conectar;
    private javax.swing.JButton b_desconectar;
    private javax.swing.JButton b_enviar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_contrasenia;
    private javax.swing.JLabel lb_direccion;
    private javax.swing.JLabel lb_puerto;
    private javax.swing.JLabel lb_usuario;
    private javax.swing.JTextArea ta_chat;
    private javax.swing.JTextField tf_chat;
    private javax.swing.JTextField tf_contrasenia;
    private javax.swing.JTextField tf_direccion;
    private javax.swing.JTextField tf_puerto;
    private javax.swing.JTextField tf_usuario;
    // End of variables declaration                   
}
