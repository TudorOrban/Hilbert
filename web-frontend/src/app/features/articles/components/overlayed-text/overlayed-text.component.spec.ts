import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OverlayedTextComponent } from './overlayed-text.component';

describe('OverlayedTextComponent', () => {
  let component: OverlayedTextComponent;
  let fixture: ComponentFixture<OverlayedTextComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OverlayedTextComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OverlayedTextComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
