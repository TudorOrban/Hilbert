import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArticleMediumCardComponent } from './article-medium-card.component';

describe('ArticleMediumCardComponent', () => {
  let component: ArticleMediumCardComponent;
  let fixture: ComponentFixture<ArticleMediumCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ArticleMediumCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ArticleMediumCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
