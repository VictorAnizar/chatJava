import java.io.*;
import java.net.*;
import java.util.*;

public class server_frame extends javax.swing.JFrame 
{
   ArrayList clientOutputStreams;
   ArrayList<String> users;

   public class ClientHandler implements Runnable   
   {
       BufferedReader reader;
       Socket sock;
       PrintWriter client;

       public ClientHandler(Socket clientSocket, PrintWriter user) 
       {
            client = user;
            try 
            {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);
            }
            catch (Exception ex) 
            {
                ta_chat.append("Error creando instancias... \n");
            }

       }

       @Override
       public void run() 
       {
            String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
            String[] data;

            try 
            {
                while ((message = reader.readLine()) != null) 
                {
                    ta_chat.append("Recibido: " + message + "\n");
                    data = message.split(":");
                    
                    

                    if (data[2].equals(connect)) 
                    {
                        tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                        userAdd(data[0]);
                    } 
                    
                    else if (data[2].equals(chat)) 
                    {
                        tellEveryone(message);
                    } 
                    else 
                    {
                        ta_chat.append("No se cumplieron las condiciones. \n");
                    }
                } 
             } 
             catch (Exception ex) 
             {
                ta_chat.append("Conexion perdida. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
             } 
    } 
    }

    public server_frame() 
    {
        initComponents();
    }

                       
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ta_chat = new javax.swing.JTextArea();
        b_iniciar = new javax.swing.JButton();
        b_finalizar = new javax.swing.JButton();
        b_usuarios = new javax.swing.JButton();
        b_limpiar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chat - VentanaServidor");
        setName("server"); // NOI18N
        setResizable(false);

        ta_chat.setColumns(20);
        ta_chat.setRows(5);
        jScrollPane1.setViewportView(ta_chat);

        b_iniciar.setText("Iniciar");
        b_iniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_iniciarActionPerformed(evt);
            }
        });

        b_finalizar.setText("Finalizar");
        b_finalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_finalizarActionPerformed(evt);
            }
        });

        b_usuarios.setText("VerUsuarios");
        b_usuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_usuariosActionPerformed(evt);
            }
        });

        b_limpiar.setText("Limpiar");
        b_limpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                b_limpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(b_finalizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b_iniciar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 285, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(b_limpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b_usuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_iniciar)
                    .addComponent(b_usuarios))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(b_limpiar)
                    .addComponent(b_finalizar))
                .addGap(22, 22, 22))
        );

        pack();
    }                       

    private void b_finalizarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        try 
        {
            Thread.sleep(5000);               
        } 
        catch(InterruptedException ex) {Thread.currentThread().interrupt();}
        
        ta_chat.append("Deteniendo servidor durante 5 segundos... \n");
        
        ta_chat.setText("");
    }                                           

    private void b_iniciarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        Thread starter = new Thread(new ServerStart());
        starter.start();
        
        ta_chat.append("Servidor iniciado...\n");
    }                                         

    private void b_usuariosActionPerformed(java.awt.event.ActionEvent evt) {                                           
        ta_chat.append("\n Usuarios activos: \n");
        for (String current_user : users)
        {
            ta_chat.append(current_user);
            ta_chat.append("\n");
        }    
        
    }                                          

    private void b_limpiarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        ta_chat.setText("");
    }                                         

    public static void main(String args[]) 
    {
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            @Override
            public void run() {
                new server_frame().setVisible(true);
            }
        });
    }
    
    public class ServerStart implements Runnable 
    {
        @Override
        public void run() 
        {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();  

            try 
            {
                ServerSocket serverSock = new ServerSocket(2222);

                while (true) 
                {
                Socket clientSock = serverSock.accept();
                PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                clientOutputStreams.add(writer);

                Thread listener = new Thread(new ClientHandler(clientSock, writer));
                listener.start();
                ta_chat.append("Con conexion. \n");
                }
            }
            catch (Exception ex)
            {
                ta_chat.append("Error iniciando conexion. \n");
            }
        }
    }
    
    public void userAdd (String data) 
    {
        String message, add = ": :Conectado", done = "Servidor: :Hecho", name = data;
        users.add(name);
        String[] tempList = new String[(users.size())];
        users.toArray(tempList);

        for (String token:tempList) 
        {
            message = (token + add);
            tellEveryone(message);
        }
        tellEveryone(done);
    }
    
   
    
    public void tellEveryone(String message) 
    {
    Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) 
        {
            try 
            {
                PrintWriter writer = (PrintWriter) it.next();
        writer.println(message);
        ta_chat.append("Enviando: " + message + "\n");
                writer.flush();
                ta_chat.setCaretPosition(ta_chat.getDocument().getLength());

            } 
            catch (Exception ex) 
            {
        ta_chat.append("Error al comunicar a todos \n");
            }
        } 
    }
    
    // Variables declaration - do not modify                     
    private javax.swing.JButton b_finalizar;
    private javax.swing.JButton b_iniciar;
    private javax.swing.JButton b_limpiar;
    private javax.swing.JButton b_usuarios;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea ta_chat;
    // End of variables declaration                   
}

