import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Window;


public class IndexComposer extends GenericForwardComposer<Window> {
  private IndexViewModel viewModel;

  @Override
  public void doAfterCompose(Window comp) throws Exception {
    super.doAfterCompose(comp);
    viewModel = new IndexViewModel();
    comp.setAttribute("vm", viewModel);
    viewModel.loadData(); // Memuat data mahasiswa saat tampilan pertama kali dibuka
  }
}
