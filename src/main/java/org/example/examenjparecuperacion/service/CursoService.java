package org.example.examenjparecuperacion.service;

import lombok.RequiredArgsConstructor;
import org.example.examenjparecuperacion.domain.Curso;
import org.example.examenjparecuperacion.proyecciones.CursoVista;
import org.example.examenjparecuperacion.repository.CursoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Page<CursoVista> buscarCursos(String busqueda, int pagina, int tamano, String ordenacion) {
        Pageable pageable = construirPageable(pagina, tamano, ordenacion);
        return procesarFiltroYBuscar(busqueda, pageable);
    }


    @Transactional(readOnly = true)
    public List<Curso> getAllCursos() {
        return cursoRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Curso getCurso(Long id) {
        return cursoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el curso"));
    }

    public Curso updateCurso(Long id, Curso curso) {
        return cursoRepository.findById(id).map(c -> {
            c.setResumen(curso.getResumen());
            c.setAlumnos(curso.getAlumnos());
            c.setPrecio(curso.getPrecio());
            c.setNivel(curso.getNivel());
            c.setHoras(curso.getHoras());
            c.setTitulo(curso.getTitulo());
            c.setResumen(curso.getResumen());

            return cursoRepository.save(c);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No se ha encontrado el curso"));
    }

    public void deleteCurso(Long id) {
        cursoRepository.deleteById(id);
    }


    private Pageable construirPageable(int pagina, int tamano, String ordenacion) {
        String campoOrden = "precio";
        Sort.Direction direccion = Sort.Direction.ASC;

        if (ordenacion != null && ordenacion.contains(",")) {
            String[] partesOrden = ordenacion.split(",");
            campoOrden = partesOrden[0];
            if (partesOrden.length > 1 && partesOrden[1].equalsIgnoreCase("desc")) {
                direccion = Sort.Direction.DESC;
            }
        }

        return PageRequest.of(pagina, tamano, Sort.by(direccion, campoOrden));
    }

    private Page<CursoVista> procesarFiltroYBuscar(String busqueda, Pageable pageable) {
        if (busqueda == null || !busqueda.contains(",")) {
            return cursoRepository.findAllProjectedBy(pageable);
        }

        String[] partesBusqueda = busqueda.split(",");
        String campoFiltro = partesBusqueda[0].toLowerCase();
        String valorFiltro = partesBusqueda.length > 1 ? partesBusqueda[1] : "";

        return enrutarConsultaBaseDatos(campoFiltro, valorFiltro, pageable);
    }

    private Page<CursoVista> enrutarConsultaBaseDatos(String campoFiltro, String valorFiltro, Pageable pageable) {
        switch (campoFiltro) {
            case "resumen":
                return cursoRepository.findByResumenContainingIgnoreCase(valorFiltro, pageable);
            case "precio":
                return buscarPorPrecioFijo(valorFiltro, pageable);
            case "titulo":
            default:
                return cursoRepository.findByTituloContainingIgnoreCase(valorFiltro, pageable);
        }
    }

    private Page<CursoVista> buscarPorPrecioFijo(String valorFiltro, Pageable pageable) {
        try {
            BigDecimal precio = new BigDecimal(valorFiltro);
            return cursoRepository.findByPrecio(precio, pageable);
        } catch (NumberFormatException e) {
            return cursoRepository.findAllProjectedBy(pageable);
        }
    }

}
