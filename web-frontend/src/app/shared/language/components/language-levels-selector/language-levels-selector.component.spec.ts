import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LanguageLevelsSelectorComponent } from './language-levels-selector.component';

describe('LanguageLevelsSelectorComponent', () => {
  let component: LanguageLevelsSelectorComponent;
  let fixture: ComponentFixture<LanguageLevelsSelectorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LanguageLevelsSelectorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LanguageLevelsSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
