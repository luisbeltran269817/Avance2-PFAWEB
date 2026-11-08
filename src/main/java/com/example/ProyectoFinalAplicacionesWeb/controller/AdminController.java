package com.example.ProyectoFinalAplicacionesWeb.controller;

import com.example.ProyectoFinalAplicacionesWeb.dto.*;
import com.example.ProyectoFinalAplicacionesWeb.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private OrdenService ordenService;

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private IdiomaService idiomaService;

    @Autowired
    private VarianteProductoService varianteProductoService;

    @GetMapping("/admin")
    public String modoAdministrador(Model model) {
        List<VarianteInventarioDTO> inventario = inventarioService.listarInventario();
        model.addAttribute("inventario", inventario);
        model.addAttribute("resumen", inventarioService.calcularResumen(inventario));
        model.addAttribute("pedidos", ordenService.listarPedidos());
        return "modoAdministrador";
    }

    @GetMapping("/admin/nuevo-titulo")
    public String nuevoTitulo() {
        return "seleccionarNuevoTitulo";
    }

    @GetMapping("/admin/productos/nuevo")
    public String nuevoProducto(Model model) {
        ProductoFormDTO form = new ProductoFormDTO();
        form.getImagenes().add(new ImagenProductoDTO());
        model.addAttribute("productoForm", form);
        model.addAttribute("categorias", categoriaService.listarCategorias());
        return "añadirProducto";
    }

    @PostMapping("/admin/productos/agregarImagen")
    public String agregarImagen(@ModelAttribute("productoForm") ProductoFormDTO form, Model model) {
        form.getImagenes().add(new ImagenProductoDTO());
        model.addAttribute("categorias", categoriaService.listarCategorias());
        return "añadirProducto";
    }

    @PostMapping("/admin/productos/guardarProducto")
    public String guardarProducto(@ModelAttribute("productoForm") ProductoFormDTO form, Model model) {
        productoService.guardarProducto(form);
        return "redirect:/admin";
    }

    @GetMapping("/admin/variantes/seleccionar")
    public String seleccionarVarianteProducto(@RequestParam(required = false) String buscar, Model model) {
        List<ProductoDTO> productos = productoService.buscarProductos(buscar);
        model.addAttribute("productos", productos);
        model.addAttribute("buscar", buscar);
        return "seleccionarVarianteProducto";
    }

    @GetMapping("/admin/variantes/nuevo")
    public String crearVarianteProducto(@RequestParam Long productoId, Model model) {
        VarianteFormDTO form = new VarianteFormDTO();
        form.setProductoId(productoId);
        model.addAttribute("varianteForm", form);
        model.addAttribute("idiomas", idiomaService.listarIdiomas());
        return "añadirVarianteProducto";
    }

    @PostMapping("/admin/variantes/guardarVariante")
    public String guardarVariante(@ModelAttribute("varianteForm") VarianteFormDTO form, Model model) {
            varianteProductoService.guardarVariante(form);
            return "redirect:/admin";
    }

    @GetMapping("/admin/productos/editar")
    public String editarProducto() {
        return "editarProducto";
    }

    @GetMapping("/admin/stock/{id}")
    public String ajusteStock(@PathVariable Long id, Model model) {
        VarianteInventarioDTO variante = inventarioService.obtenerVariante(id);
        if (variante == null) {
            return "redirect:/admin";
        }
        model.addAttribute("variante", variante);
        return "ajusteStock";
    }

    @PostMapping("/admin/stock/{id}")
    public String guardarAjuste(@PathVariable Long id,
                                @RequestParam("cantidad") int cantidad,
                                @RequestParam("motivo") String motivo) {
        boolean registrado = inventarioService.registrarAjuste(id, cantidad, motivo);
        if (!registrado) {
            return "redirect:/admin/stock/" + id;
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/pedidos/{id}/estado")
    public String cambiarEstado(@PathVariable Long id) {
        ordenService.avanzarEstado(id);
        return "redirect:/admin";
    }

     @GetMapping("/admin/editarCategoria")
    public String editarCategorias() {
        return "editarCategoria";
    }

    @PostMapping("/admin/editarCategoria")
    public String guardarCategorias() {
        //falta ponerle la logica de guardado
        //solo redirige al admin dashboard por ahora
        return "redirect:/admin";
    }

    @PostMapping("/admin/editarCategoria/{id}/eliminar")
    public String eliminarCategoria(@PathVariable Long id) {
        //falta ponerle la logica de eliminado
        return "redirect:/admin/categorias";
    }

    @GetMapping("/admin/productos/{id}/editar")
    public String editarProducto(@PathVariable Long id, Model model) {
        ProductoEdicionDTO productoEdicionDTO = productoService.buscarVarianteProductoPorId(id);
        if (productoEdicionDTO == null) {
            return "redirect:/admin";
        }
        model.addAttribute("productoEdicion", productoEdicionDTO);
        model.addAttribute("categorias", categoriaService.listarCategorias());
        return "editarProducto";
    }

    @PostMapping("/admin/productos/{id}/editar")
    public String guardarEdicion(@PathVariable Long id, @ModelAttribute("productoEdicion") ProductoEdicionDTO productoEdicionDTO) {
        productoService.actualizarProducto(id, productoEdicionDTO);
        return "redirect:/admin";
    }
}
