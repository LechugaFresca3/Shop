package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Product;
import model.Sale;
import java.util.Scanner;
import model.Amount;
import model.Client;
import model.Employee;

public class Shop {

    private Amount cash = new Amount(100.00);
    public static ArrayList<Product> inventory = new ArrayList<>();
    private ArrayList<Sale> sales = new ArrayList<>();
    public static int counterSales = 0;
    private Employee employee1 = null;

    final static double TAX_RATE = 1.04;

    public Amount getCash() {
        return cash;
    }

    public void setCash(Amount cash) {
        this.cash = cash;
    }

    public static void main(String[] args) {
        Shop shop = new Shop();

        shop.loadInventory();
        shop.initSession();
        Scanner scanner = new Scanner(System.in);

        int opcion = 0;
        boolean exit = false;

        do {
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Menu principal miTienda.com");
            System.out.println("===========================");
            System.out.println("1) Contar caja");
            System.out.println("2) Añadir producto");
            System.out.println("3) Añadir stock");
            System.out.println("4) Marcar producto próxima caducidad");
            System.out.println("5) Ver inventario");
            System.out.println("6) Venta");
            System.out.println("7) Ver ventas");
            System.out.println("8) Todas las ventas");
            System.out.println("9) Eliminar producto");
            System.out.println("10) Salir programa");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    shop.showCash();
                    break;

                case 2:
                    shop.addProduct();
                    break;

                case 3:
                    shop.addStock();
                    break;

                case 4:
                    shop.setExpired();
                    break;

                case 5:
                    shop.showInventory();
                    break;

                case 6:
                    shop.sale();
                    break;

                case 7:
                    shop.showSales();
                    break;

                case 8:
                    shop.amountSales();
                    break;

                case 9:
                    shop.EliminarP();
                    break;

                case 10:
                    exit = true;
                    break;
            }
        } while (!exit);
    }

    public void initSession() {
        Scanner sc = new Scanner(System.in);
        boolean verificar = false;

        while (!verificar) {
            System.out.println("Cual es tu numero de empleado?");
            int employeeld = sc.nextInt();

            // tienes que coger y usar el employeeId de la clase Employee
            System.out.println("Introduce tu contraseña:");
            String password = sc.next();

            sc.nextLine();
            // tienes que coger y usar el password de la clase Employee

            Employee employee = new Employee(employeeld, password, "Employee");

            if (employee.login(employeeld, password)) {
                employee1 = employee;
                System.out.println("Inicio de sesion correcto");
                System.out.println("Bienvenido empleado");
                verificar = true;
            } else {
                System.out.println("Usuario o contraseña incorrectos");
            }
        }

    }

    public void loadInventory() {
        //addProduct(new Product("Manzana", new Amount(10.00), true, 10));
        //addProduct(new Product("Pera", new Amount(20.00), true, 20));
        //addProduct(new Product("Hamburguesa", new Amount(30.00), true, 30));
        //addProduct(new Product("Fresa",new Amount(5.00), true, 20));

        readInventory();

    }

    public void readInventory() {
        //lo que hace es que crea un archivo nuevo, hace ruta absoluta, separa las carpetas y por ultima la ruta de donde esta
        File doc = new File(System.getProperty("user.dir") + File.separator + "files/inputInventory.txt");

        // como me daba error usamos el try, para que pruebe y si falla salta al catch
        try {
            //hace un stream para conectar los datos
            FileReader fr = new FileReader(doc);
            //Buffer que almacena los datos del stream
            BufferedReader br = new BufferedReader(fr);
            //lee los datos del buffer
            String linea = br.readLine();
            //Al leer un archivo tendremos que seguir dichos pasos

            //Mientras hayan lineas...
            while (linea != null) {
                String[] partes = linea.split(";");

                // partes[0] = "Product:Manzana"
                String partNombre = partes[0];
                String[] splitNombre = partNombre.split(":");
                String nombre = splitNombre[1];

                // partes[1] = "Wholesaler Price:10.00"
                String partPrecio = partes[1];
                String[] splitPrecio = partPrecio.split(":");
                double precio = Double.parseDouble(splitPrecio[1]);

                // partes[2] = "Stock:10"
                String partStock = partes[2];
                String[] splitStock = partStock.split(":");
                int stock = Integer.parseInt(splitStock[1]);

                addProduct(new Product(nombre, new Amount(precio), true, stock));

                linea = br.readLine();
            }

            br.close();

        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el fichero: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al leer el fichero: " + e.getMessage());
        }
    }

    public void showCash() {
        System.out.println("Dinero actual: " + cash);
    }

    public void addProduct() {
        if (isInventoryFull()) {
            System.out.println("No se pueden añadir más productos");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        if (findProduct(name) == null) {
            System.out.print("Precio mayorista: ");
            double wholesalerPrice = scanner.nextDouble();
            System.out.print("Stock: ");
            int stock = scanner.nextInt();

            addProduct(new Product(name, new Amount(wholesalerPrice), true, stock));

        } else {
            System.out.println("Ya existe el producto");
        }
    }

    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.nextLine();
        Product product = findProduct(name);

        if (product != null) {
            System.out.print("Seleccione la cantidad a añadir: ");
            int stock = scanner.nextInt();
            product.setStock(product.getStock() + stock);
            System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());

        } else {
            System.out.println("No se ha encontrado el producto con nombre " + name);
        }
    }

    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Seleccione un nombre de producto: ");
        String name = scanner.next();

        Product product = findProduct(name);

        if (product != null) {
            product.expire();
            System.out.println("El precio del producto " + name + " ha sido actualizado a " + product.getPublicPrice());
        }
    }

    public void showInventory() {
        System.out.println("Contenido actual de la tienda:");
        for (Product product : inventory) {
            if (product != null) {
                System.out.println(product);
            }
        }
    }

    public void sale() {

        ArrayList<Product> shoppingcart = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        System.out.println("Realizar venta, escribir nombre cliente:");
        String clientName = sc.nextLine();

        double totalAmount = 0.0;
        String name = "";

        while (!name.equals("0")) {
            System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
            name = sc.nextLine();

            if (name.equals("0")) {
                break;
            }

            Product product = findProduct(name);

            if (product != null && product.isAvailable()) {
                totalAmount += product.getPublicPrice().getValue();
                product.setStock(product.getStock() - 1);

                if (product.getStock() == 0) {
                    product.setAvailable(false);
                }

                shoppingcart.add(product);
                System.out.println("Producto añadido con éxito");

            } else {
                System.out.println("Producto no encontrado o sin stock");
            }
        }

        totalAmount = totalAmount * TAX_RATE;
        cash.setValue(cash.getValue() + totalAmount);

        System.out.println("Venta realizada con éxito, total: " + totalAmount);

        // Guardar venta
        Client client = new Client(clientName);
        Sale newSale = new Sale(client, shoppingcart, new Amount(totalAmount));
        sales.add(newSale);
        counterSales++;

        // Crear objeto de tipo Client
        boolean succes = client.pay(new Amount(totalAmount));
        if (succes) {
            System.out.println(client.getBalance());
        } else {
            System.out.println("Debes esta cantidad");
            System.out.println(client.getBalance());
        }

    }

    private void showSales() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Lista de ventas:");
        if (sales != null) {
            for (Sale sale : sales) {
                System.out.println(sale);
            }
        }

        System.out.println("Deseas exportar el archivo? (si/no)");
        String export = sc.nextLine();
        if (export.equals("si")) {
            writeSales();
        }

    }

    public void writeSales() {
        LocalDate fechaActual = LocalDate.now();
        String fecha = fechaActual.toString();
        String nombreFichero = "sales_" + fecha + ".txt";
        String ruta = System.getProperty("user.dir") + File.separator + "files" + File.separator + nombreFichero;
        try {
            FileWriter fw = new FileWriter(ruta);
            BufferedWriter bw = new BufferedWriter(fw);
            int numeroVenta = 1;
            for (Sale sale : sales) {
                bw.write(numeroVenta + ";Client=" + sale.getClient());
                bw.newLine();
                String productos = "";
                for (Product p : sale.getProducts()) {
                    productos += p.getName() + "," + p.getPublicPrice() + "?;";
                }
                bw.write(numeroVenta + ";Products=" + productos);
                bw.newLine();
                bw.write(numeroVenta + ";Amount=" + sale.getAmount());
                bw.newLine();
                numeroVenta++;
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error al escribir el fichero: " + e.getMessage());
        }
    }

    public void addProduct(Product product) {
        if (isInventoryFull()) {
            System.out.println("No se pueden añadir más productos");
            return;
        }
        inventory.add(product);
        this.showInventory();
    }

    public boolean isInventoryFull() {
        return false;
    }

    public Product findProduct(String name) {
        for (Product p : inventory) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    public void amountSales() {
        double total = 0.0;

        for (Sale s : sales) {
            total += s.getAmount().getValue();
        }

        System.out.println("El importe total de todas las ventas es: " + total + " euros");
    }

    public void EliminarP() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Cual producto quieres eliminar?");
        String name = sc.nextLine();
        Product p = null;

        for (Product product : inventory) {
            if (product.getName().equals(name)) {
                p = product;
                break;
            }
        }
        if (p != null) {
            inventory.remove(p);
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Producto no encontrado.");
        }

    }
}
