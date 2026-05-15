package besededatos;

import besededatos.models.*;
import besededatos.controllers.*;
import besededatos.config.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class TestInsercionCompleto {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("   SISTEMA DE INSERCIÓN DE DATOS DE PRUEBA");
        System.out.println("   INDUSTRIA R.G - VENTANERÍA");

        Connection conn = null;
        try {
            // Usar método estático en lugar de instanciar
            conn = Conexion.establecerConexion();

            if (conn == null) {
                System.out.println("✗ Error: No se pudo conectar a la base de datos.");
                return;
            }
            System.out.println("✓ Conexión exitosa a la base de datos.\n");

            mostrarMenuPrincipal(conn);

        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    private static void mostrarMenuPrincipal(Connection conn) throws SQLException {
        int opcion;
        do {
            System.out.println("\n           MENÚ PRINCIPAL");
            System.out.println("1. Insertar DESGLOSE");
            System.out.println("2. Insertar COMPRA");
            System.out.println("3. Insertar ORDEN DE TRABAJO");
            System.out.println("4. Insertar INSTALACIÓN");
            System.out.println("5. Ver DATOS DE TODAS LAS TABLAS");
            System.out.println("6. LIMPIAR TABLAS (Eliminar datos de prueba)");
            System.out.println("0. SALIR");
            System.out.print("\nSeleccione una opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    insertarDesglose(conn);
                    break;
                case 2:
                    insertarCompra(conn);
                    break;
                case 3:
                    insertarOrdenTrabajo(conn);
                    break;
                case 4:
                    insertarInstalacion(conn);
                    break;
                case 5:
                    verTodasLasTablas(conn);
                    break;
                case 6:
                    limpiarTablas(conn);
                    break;
                case 0:
                    System.out.println("\n¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    private static void insertarDesglose(Connection conn) throws SQLException {
        System.out.println("\n--- INSERTAR NUEVO DESGLOSE ---\n");

        mostrarClientes(conn);
        System.out.print("ID del Cliente: ");
        int clienteId = Integer.parseInt(sc.nextLine());

        mostrarTiposVentana(conn);
        System.out.print("ID del Tipo de Ventana: ");
        int tipoVentanaId = Integer.parseInt(sc.nextLine());

        System.out.print("Código del Desglose (ej: VEN-2025-001): ");
        String codigo = sc.nextLine();

        System.out.print("Ancho (m): ");
        double ancho = Double.parseDouble(sc.nextLine());

        System.out.print("Alto (m): ");
        double alto = Double.parseDouble(sc.nextLine());

        System.out.print("Costo Materiales (₡): ");
        double costoMateriales = Double.parseDouble(sc.nextLine());

        System.out.print("Mano de Obra (₡): ");
        double manoObra = Double.parseDouble(sc.nextLine());

        System.out.print("Gastos Indirectos (₡): ");
        double gastosIndirectos = Double.parseDouble(sc.nextLine());

        System.out.print("Precio de Venta (₡): ");
        double precioVenta = Double.parseDouble(sc.nextLine());

        // Materiales adicionales (opcionales)
        System.out.print("Tipo Perfil (opcional, Enter para usar 'Aluminio Estándar'): ");
        String tipoPerfil = sc.nextLine();
        if (tipoPerfil.isEmpty()) tipoPerfil = "Aluminio Estándar";

        System.out.print("Grosor (mm) (opcional, Enter para usar '1.5'): ");
        String grosor = sc.nextLine();
        if (grosor.isEmpty()) grosor = "1.5";

        System.out.print("Tipo Vidrio (opcional, Enter para usar 'Templado'): ");
        String tipoVidrio = sc.nextLine();
        if (tipoVidrio.isEmpty()) tipoVidrio = "Templado";

        System.out.print("Espesor (mm) (opcional, Enter para usar '6'): ");
        String espesor = sc.nextLine();
        if (espesor.isEmpty()) espesor = "6";

        System.out.print("Herrajes (opcional, Enter para usar 'Ferrex'): ");
        String herrajes = sc.nextLine();
        if (herrajes.isEmpty()) herrajes = "Ferrex";

        System.out.print("Sellador (opcional, Enter para usar 'Silicona'): ");
        String sellador = sc.nextLine();
        if (sellador.isEmpty()) sellador = "Silicona";

        System.out.print("Acabado (opcional, Enter para usar 'Electrostático'): ");
        String acabado = sc.nextLine();
        if (acabado.isEmpty()) acabado = "Electrostático";

        System.out.print("Cantidad de Hojas: ");
        int cantHojas = Integer.parseInt(sc.nextLine());

        String sql = "INSERT INTO Desglose (codigo, cliente_id, tipo_ventana_id, ancho, alto, " +
                "tipo_perfil, grosor_mm, tipo_vidrio, espesor_mm, herrajes, sellador, acabado, " +
                "cant_hojas, costo_materiales, mano_obra, gastos_indirectos, precio_venta) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, codigo);
            pstmt.setInt(2, clienteId);
            pstmt.setInt(3, tipoVentanaId);
            pstmt.setDouble(4, ancho);
            pstmt.setDouble(5, alto);
            pstmt.setString(6, tipoPerfil);
            pstmt.setString(7, grosor);
            pstmt.setString(8, tipoVidrio);
            pstmt.setString(9, espesor);
            pstmt.setString(10, herrajes);
            pstmt.setString(11, sellador);
            pstmt.setString(12, acabado);
            pstmt.setInt(13, cantHojas);
            pstmt.setDouble(14, costoMateriales);
            pstmt.setDouble(15, manoObra);
            pstmt.setDouble(16, gastosIndirectos);
            pstmt.setDouble(17, precioVenta);

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    System.out.println("\n✓ DESGLOSE INSERTADO CORRECTAMENTE!");
                    System.out.println("   ID: " + rs.getInt(1));
                    System.out.println("   Código: " + codigo);
                    System.out.println("   Total: ₡" + (costoMateriales + manoObra + gastosIndirectos));
                }
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar desglose: " + e.getMessage());
        }
    }

    private static void insertarCompra(Connection conn) throws SQLException {
        System.out.println("\n--- INSERTAR NUEVA COMPRA ---\n");

        mostrarProveedores(conn);
        System.out.print("ID del Proveedor: ");
        int proveedorId = Integer.parseInt(sc.nextLine());

        mostrarMateriales(conn);
        System.out.print("ID del Material: ");
        int materialId = Integer.parseInt(sc.nextLine());

        System.out.print("Código de Compra (ej: OC-2025-001): ");
        String codigo = sc.nextLine();

        System.out.print("Cantidad: ");
        double cantidad = Double.parseDouble(sc.nextLine());

        System.out.print("Precio Unitario (₡): ");
        double precioUnitario = Double.parseDouble(sc.nextLine());

        double total = cantidad * precioUnitario;
        System.out.println("Total Automático: ₡" + total);

        System.out.print("Estado (Pendiente/Enviada/En Tránsito/Recibida/Cancelada): ");
        String estado = sc.nextLine();

        System.out.print("Fecha de Entrega (YYYY-MM-DD): ");
        LocalDate fechaEntrega = LocalDate.parse(sc.nextLine());

        String sql = "INSERT INTO Compra (codigo, proveedor_id, material_id, fecha_entrega, " +
                "cantidad, precio_unitario, total, estado) VALUES (?,?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, codigo);
            pstmt.setInt(2, proveedorId);
            pstmt.setInt(3, materialId);
            pstmt.setDate(4, Date.valueOf(fechaEntrega));
            pstmt.setDouble(5, cantidad);
            pstmt.setDouble(6, precioUnitario);
            pstmt.setDouble(7, total);
            pstmt.setString(8, estado);

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    System.out.println("\n✓ COMPRA REGISTRADA CORRECTAMENTE!");
                    System.out.println("   ID: " + rs.getInt(1));
                    System.out.println("   Código: " + codigo);
                    System.out.println("   Total: ₡" + total);
                }
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar compra: " + e.getMessage());
        }
    }

    private static void insertarOrdenTrabajo(Connection conn) throws SQLException {
        System.out.println("\n--- INSERTAR NUEVA ORDEN DE TRABAJO ---\n");

        mostrarClientes(conn);
        System.out.print("ID del Cliente: ");
        int clienteId = Integer.parseInt(sc.nextLine());

        mostrarDesgloses(conn);
        System.out.print("ID del Desglose: ");
        int desgloseId = Integer.parseInt(sc.nextLine());

        mostrarTecnicos(conn);
        System.out.print("ID del Técnico: ");
        int tecnicoId = Integer.parseInt(sc.nextLine());

        System.out.print("Código de Orden (ej: OT-2025-001): ");
        String codigo = sc.nextLine();

        System.out.print("Fecha Inicio (YYYY-MM-DD): ");
        LocalDate fechaInicio = LocalDate.parse(sc.nextLine());

        System.out.print("Fecha Estimada (YYYY-MM-DD): ");
        LocalDate fechaEstimada = LocalDate.parse(sc.nextLine());

        System.out.print("Estado (Pendiente/En Producción/Lista para Instalar/Instalada/Completada): ");
        String estado = sc.nextLine();

        System.out.print("Observaciones: ");
        String observaciones = sc.nextLine();

        String sql = "INSERT INTO OrdenTrabajo (codigo, cliente_id, desglose_id, tecnico_id, " +
                "fecha_inicio, fecha_estimada, estado, observaciones) VALUES (?,?,?,?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, codigo);
            pstmt.setInt(2, clienteId);
            pstmt.setInt(3, desgloseId);
            pstmt.setInt(4, tecnicoId);
            pstmt.setDate(5, Date.valueOf(fechaInicio));
            pstmt.setDate(6, Date.valueOf(fechaEstimada));
            pstmt.setString(7, estado);
            pstmt.setString(8, observaciones);

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    System.out.println("\n✓ ORDEN DE TRABAJO REGISTRADA CORRECTAMENTE!");
                    System.out.println("   ID: " + rs.getInt(1));
                    System.out.println("   Código: " + codigo);
                }
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar orden de trabajo: " + e.getMessage());
        }
    }

    private static void insertarInstalacion(Connection conn) throws SQLException {
        System.out.println("\n--- PROGRAMAR NUEVA INSTALACIÓN ---\n");

        mostrarOrdenesPendientes(conn);
        System.out.print("ID de la Orden de Trabajo: ");
        int ordenTrabajoId = Integer.parseInt(sc.nextLine());

        mostrarTecnicos(conn);
        System.out.print("ID del Técnico: ");
        int tecnicoId = Integer.parseInt(sc.nextLine());

        System.out.print("Fecha de Instalación (YYYY-MM-DD): ");
        LocalDate fecha = LocalDate.parse(sc.nextLine());

        System.out.print("Hora (08:00, 09:00, etc): ");
        String hora = sc.nextLine();

        System.out.print("Estado (Programada/En Proceso/Completada/Cancelada): ");
        String estado = sc.nextLine();

        System.out.print("Notas/Instrucciones: ");
        String notas = sc.nextLine();

        String sql = "INSERT INTO Instalacion (orden_trabajo_id, tecnico_id, fecha, hora, estado, notas) " +
                "VALUES (?,?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, ordenTrabajoId);
            pstmt.setInt(2, tecnicoId);
            pstmt.setDate(3, Date.valueOf(fecha));
            pstmt.setString(4, hora);
            pstmt.setString(5, estado);
            pstmt.setString(6, notas);

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    System.out.println("\n✓ INSTALACIÓN PROGRAMADA CORRECTAMENTE!");
                    System.out.println("   ID: " + rs.getInt(1));
                    System.out.println("   Fecha: " + fecha + " - " + hora);
                }
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar instalación: " + e.getMessage());
        }
    }

    private static void verTodasLasTablas(Connection conn) throws SQLException {
        System.out.println("\n           DATOS REGISTRADOS\n");

        System.out.println("=== DESGLOSES ===");
        String sqlDesglose = "SELECT d.id, d.codigo, c.nombre AS cliente, tv.nombre AS tipo_ventana, " +
                "d.ancho, d.alto, d.precio_venta FROM Desglose d " +
                "JOIN Cliente c ON d.cliente_id = c.id " +
                "JOIN TipoVentana tv ON d.tipo_ventana_id = tv.id " +
                "ORDER BY d.id DESC LIMIT 10";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlDesglose)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("   No hay desgloses registrados.");
            } else {
                while (rs.next()) {
                    System.out.printf("   ID:%d | %s | %s | %.2fx%.2f | ₡%.2f%n",
                            rs.getInt("id"), rs.getString("codigo"), rs.getString("cliente"),
                            rs.getDouble("ancho"), rs.getDouble("alto"), rs.getDouble("precio_venta"));
                }
            }
        }

        System.out.println("\n=== COMPRAS ===");
        String sqlCompra = "SELECT c.id, c.codigo, p.nombre AS proveedor, m.nombre AS material, " +
                "c.cantidad, c.total, c.estado FROM Compra c " +
                "JOIN Proveedor p ON c.proveedor_id = p.id " +
                "JOIN Material m ON c.material_id = m.id " +
                "ORDER BY c.id DESC LIMIT 10";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlCompra)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("   No hay compras registradas.");
            } else {
                while (rs.next()) {
                    System.out.printf("   ID:%d | %s | %s | %.0f und | ₡%.2f | %s%n",
                            rs.getInt("id"), rs.getString("codigo"), rs.getString("proveedor"),
                            rs.getDouble("cantidad"), rs.getDouble("total"), rs.getString("estado"));
                }
            }
        }

        System.out.println("\n=== ÓRDENES DE TRABAJO ===");
        String sqlOrden = "SELECT o.id, o.codigo, c.nombre AS cliente, t.nombre AS tecnico, " +
                "o.estado FROM OrdenTrabajo o " +
                "JOIN Cliente c ON o.cliente_id = c.id " +
                "JOIN Tecnico t ON o.tecnico_id = t.id " +
                "ORDER BY o.id DESC LIMIT 10";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlOrden)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("   No hay órdenes de trabajo registradas.");
            } else {
                while (rs.next()) {
                    System.out.printf("   ID:%d | %s | %s | Técnico: %s | %s%n",
                            rs.getInt("id"), rs.getString("codigo"), rs.getString("cliente"),
                            rs.getString("tecnico"), rs.getString("estado"));
                }
            }
        }

        System.out.println("\n=== INSTALACIONES ===");
        String sqlInstalacion = "SELECT i.id, o.codigo AS orden, i.fecha, i.hora, t.nombre AS tecnico, i.estado " +
                "FROM Instalacion i JOIN OrdenTrabajo o ON i.orden_trabajo_id = o.id " +
                "JOIN Tecnico t ON i.tecnico_id = t.id " +
                "ORDER BY i.id DESC LIMIT 10";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sqlInstalacion)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("   No hay instalaciones programadas.");
            } else {
                while (rs.next()) {
                    System.out.printf("   ID:%d | Orden: %s | %s %s | Técnico: %s | %s%n",
                            rs.getInt("id"), rs.getString("orden"), rs.getString("fecha"),
                            rs.getString("hora"), rs.getString("tecnico"), rs.getString("estado"));
                }
            }
        }
        System.out.println();
    }

    private static void limpiarTablas(Connection conn) throws SQLException {
        System.out.print("\n⚠ ¿Está seguro de eliminar TODOS los datos de prueba? (s/n): ");
        String confirmacion = sc.nextLine();

        if (confirmacion.equalsIgnoreCase("s")) {
            boolean autoCommitOriginal = conn.getAutoCommit();
            try {
                conn.setAutoCommit(false);

                // Eliminar en orden correcto (por claves foráneas)
                System.out.println("Eliminando instalaciones...");
                int instalaciones = conn.createStatement().executeUpdate("DELETE FROM Instalacion");
                System.out.println("   Eliminadas: " + instalaciones);

                System.out.println("Eliminando órdenes de trabajo...");
                int ordenes = conn.createStatement().executeUpdate("DELETE FROM OrdenTrabajo");
                System.out.println("   Eliminadas: " + ordenes);

                System.out.println("Eliminando compras...");
                int compras = conn.createStatement().executeUpdate("DELETE FROM Compra");
                System.out.println("   Eliminadas: " + compras);

                System.out.println("Eliminando desgloses...");
                int desgloses = conn.createStatement().executeUpdate("DELETE FROM Desglose");
                System.out.println("   Eliminados: " + desgloses);

                conn.commit();
                System.out.println("\n✓ TODOS LOS DATOS DE PRUEBA HAN SIDO ELIMINADOS!");

            } catch (SQLException e) {
                conn.rollback();
                System.err.println("Error al limpiar tablas: " + e.getMessage());
                System.err.println("   Operación revertida.");
            } finally {
                conn.setAutoCommit(autoCommitOriginal);
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    private static void mostrarClientes(Connection conn) throws SQLException {
        System.out.println("\n--- CLIENTES DISPONIBLES ---");
        String sql = "SELECT id, nombre FROM Cliente ORDER BY id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("   No hay clientes registrados.");
            } else {
                while (rs.next()) {
                    System.out.printf("   %d: %s%n", rs.getInt("id"), rs.getString("nombre"));
                }
            }
        }
    }

    private static void mostrarTiposVentana(Connection conn) throws SQLException {
        System.out.println("\n--- TIPOS DE VENTANA ---");
        String sql = "SELECT id, nombre FROM TipoVentana ORDER BY id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("   No hay tipos de ventana registrados.");
            } else {
                while (rs.next()) {
                    System.out.printf("   %d: %s%n", rs.getInt("id"), rs.getString("nombre"));
                }
            }
        }
    }

    private static void mostrarProveedores(Connection conn) throws SQLException {
        System.out.println("\n--- PROVEEDORES ---");
        String sql = "SELECT id, nombre FROM Proveedor ORDER BY id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("   No hay proveedores registrados.");
            } else {
                while (rs.next()) {
                    System.out.printf("   %d: %s%n", rs.getInt("id"), rs.getString("nombre"));
                }
            }
        }
    }

    private static void mostrarMateriales(Connection conn) throws SQLException {
        System.out.println("\n--- MATERIALES ---");
        String sql = "SELECT id, nombre FROM Material ORDER BY id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("   No hay materiales registrados.");
            } else {
                while (rs.next()) {
                    System.out.printf("   %d: %s%n", rs.getInt("id"), rs.getString("nombre"));
                }
            }
        }
    }

    private static void mostrarDesgloses(Connection conn) throws SQLException {
        System.out.println("\n--- DESGLOSES DISPONIBLES ---");
        String sql = "SELECT id, codigo FROM Desglose ORDER BY id DESC LIMIT 10";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("   No hay desgloses registrados.");
            } else {
                while (rs.next()) {
                    System.out.printf("   %d: %s%n", rs.getInt("id"), rs.getString("codigo"));
                }
            }
        }
    }

    private static void mostrarTecnicos(Connection conn) throws SQLException {
        System.out.println("\n--- TÉCNICOS ---");
        String sql = "SELECT id, nombre FROM Tecnico ORDER BY id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("   No hay técnicos registrados.");
            } else {
                while (rs.next()) {
                    System.out.printf("   %d: %s%n", rs.getInt("id"), rs.getString("nombre"));
                }
            }
        }
    }

    private static void mostrarOrdenesPendientes(Connection conn) throws SQLException {
        System.out.println("\n--- ÓRDENES DE TRABAJO (Listas para instalar) ---");
        String sql = "SELECT id, codigo FROM OrdenTrabajo WHERE estado = 'Lista para Instalar' ORDER BY id";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            if (!rs.isBeforeFirst()) {
                System.out.println("   No hay órdenes listas para instalar.");
            } else {
                while (rs.next()) {
                    System.out.printf("   %d: %s%n", rs.getInt("id"), rs.getString("codigo"));
                }
            }
        }
    }
}