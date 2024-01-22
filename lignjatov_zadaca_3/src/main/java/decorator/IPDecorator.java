package decorator;

import entity.UredDostave;

public abstract class IPDecorator implements IIspisTekstaIP {
    private IIspisTekstaIP wrapped;

    public IPDecorator(IIspisTekstaIP wrappee){
        wrapped = wrappee;
    }

    @Override
    public void ispisiPodatke(UredDostave ured) {
        wrapped.ispisiPodatke(ured);
    }

}
